import json
import sys
import os

# Load the OpenAPI spec
with open('openapi.json', 'r') as f:
    spec = json.load(f)

def resolve_schema_ref(ref_path):
    """Resolve $ref to schema name"""
    if ref_path.startswith('#/components/schemas/'):
        return ref_path.split('/')[-1]
    return None

def get_response_type(responses, operation_id=''):
    """Extract the response type from responses"""
    if '200' in responses:
        content = responses['200'].get('content', {})

        # Handle JSON responses
        if 'application/json' in content:
            schema = content['application/json'].get('schema', {})
            if '$ref' in schema:
                return resolve_schema_ref(schema['$ref'])
            elif 'oneOf' in schema or 'anyOf' in schema:
                # Generate a sealed class type name based on operation ID
                type_name = ''.join(word.capitalize() for word in operation_id.split('_')) if operation_id else 'Response'
                return type_name
            elif schema.get('type') == 'array':
                items = schema.get('items', {})
                if '$ref' in items:
                    item_type = resolve_schema_ref(items['$ref'])
                    return f'List<{item_type}>'
                elif 'oneOf' in items or 'anyOf' in items:
                    # Generate a sealed class for array items
                    type_name = ''.join(word.capitalize() for word in operation_id.split('_')) + 'Item' if operation_id else 'ResponseItem'
                    return f'List<{type_name}>'
                elif items.get('type') == 'array':
                    return 'List<List<Int>>'
            elif schema.get('type') == 'object':
                if 'additionalProperties' in schema:
                    additional = schema['additionalProperties']
                    if additional is True or additional == {}:
                        return 'JsonObject'  # Use JsonObject instead of Map<String, Any>
                    elif '$ref' in additional:
                        value_type = resolve_schema_ref(additional['$ref'])
                        return f'Map<String, {value_type}>'
                    else:
                        # Generate nested type for additionalProperties
                        return 'JsonObject'
                elif 'properties' in schema:
                    # Generate inline object type
                    type_name = ''.join(word.capitalize() for word in operation_id.split('_')) if operation_id else 'Response'
                    return type_name

        # Handle NDJSON streaming responses
        elif 'application/x-ndjson' in content:
            schema = content['application/x-ndjson'].get('schema', {})
            if '$ref' in schema:
                type_name = resolve_schema_ref(schema['$ref'])
                return f'Flow<{type_name}>'
            elif 'oneOf' in schema or 'anyOf' in schema:
                # Generate a sealed class type name for streaming
                type_name = ''.join(word.capitalize() for word in operation_id.split('_')) + 'Event' if operation_id else 'StreamEvent'
                return f'Flow<{type_name}>'
            elif schema.get('type') == 'array':
                items = schema.get('items', {})
                if '$ref' in items:
                    item_type = resolve_schema_ref(items['$ref'])
                    return f'Flow<{item_type}>'
                elif 'oneOf' in items or 'anyOf' in items:
                    type_name = ''.join(word.capitalize() for word in operation_id.split('_')) + 'Item' if operation_id else 'StreamItem'
                    return f'Flow<{type_name}>'

        # Handle text responses
        elif 'text/plain' in content:
            return 'String'

        # Handle PGN responses
        elif 'application/x-chess-pgn' in content:
            return 'String'

    return 'Unit'

def get_kotlin_type(schema):
    """Convert JSON schema type to Kotlin type"""
    if not schema:
        return 'String'

    schema_type = schema.get('type', 'string')

    if schema_type == 'integer':
        return 'Int'
    elif schema_type == 'number':
        return 'Double'
    elif schema_type == 'boolean':
        return 'Boolean'
    elif schema_type == 'array':
        items = schema.get('items', {})
        if items.get('type') == 'string':
            return 'List<String>'
        item_type = get_kotlin_type(items) if items else 'String'
        return f'List<{item_type}>'
    else:
        return 'String'

def sanitize_param_name(name):
    """Sanitize parameter names that are Kotlin keywords or contain invalid characters"""
    kotlin_keywords = ['object', 'in', 'is', 'as', 'when', 'fun', 'val', 'var']

    # Replace dots with underscores or camelCase
    if '.' in name:
        parts = name.split('.')
        # Convert to camelCase: clock.limit -> clockLimit
        sanitized = parts[0] + ''.join(p.capitalize() for p in parts[1:])
        return sanitized

    if name in kotlin_keywords:
        return f'`{name}`'
    return name

def sanitize_tag_name(tag_name):
    """Sanitize tag name to be a valid Kotlin class name"""
    # Remove or replace special characters and spaces
    # Convert to PascalCase
    words = tag_name.replace('-', ' ').replace('_', ' ').split()
    sanitized = ''.join(word.capitalize() for word in words)
    return sanitized

def generate_function_name(operation_id, method, path):
    """Generate a camelCase function name"""
    if operation_id:
        name = operation_id
        if name.startswith('api'):
            name = name[3:]
        name = name[0].lower() + name[1:] if name else 'unknown'
        return name

    path_parts = [p for p in path.split('/') if p and not p.startswith('{')]
    method_lower = method.lower()
    if path_parts:
        return method_lower + ''.join(word.capitalize() for word in path_parts)
    return method_lower + 'Request'

def get_all_tags():
    """Extract all unique tags from the OpenAPI spec"""
    tags = set()
    for path, methods in spec.get('paths', {}).items():
        for method, details in methods.items():
            if method in ['get', 'post', 'put', 'delete', 'patch']:
                endpoint_tags = details.get('tags', [])
                tags.update(endpoint_tags)
    return sorted(list(tags))

def extract_endpoints_by_tag(tag_name):
    """Extract all endpoints for a specific tag"""
    endpoints = []
    for path, methods in spec.get('paths', {}).items():
        for method, details in methods.items():
            if method in ['get', 'post', 'put', 'delete', 'patch']:
                tags = details.get('tags', [])
                if tag_name in tags:
                    endpoints.append({
                        'path': path,
                        'method': method.upper(),
                        'operationId': details.get('operationId', ''),
                        'summary': details.get('summary', ''),
                        'description': details.get('description', ''),
                        'parameters': details.get('parameters', []),
                        'requestBody': details.get('requestBody'),
                        'responses': details.get('responses', {})
                    })
    return endpoints

def generate_service_for_tag(tag_name):
    """Generate a complete service class for a given tag"""
    endpoints = extract_endpoints_by_tag(tag_name)

    if not endpoints:
        print(f'⚠️  No endpoints found for tag: {tag_name}')
        return None

    # Sanitize tag name for class name
    sanitized_tag = sanitize_tag_name(tag_name)

    # Generate service class
    service_code = f'''package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for {tag_name} API endpoints
 * Provides methods to interact with Lichess {tag_name.lower()} data
 */
class {sanitized_tag}Service(
    private val apiClient: BaseApiClient
) {{
'''

    for endpoint in endpoints:
        path = endpoint['path']
        method = endpoint['method']
        operation_id = endpoint['operationId']
        summary = endpoint['summary']
        description = endpoint['description']
        parameters = endpoint['parameters']
        request_body = endpoint['requestBody']
        responses = endpoint['responses']

        func_name = generate_function_name(operation_id, method, path)
        response_type = get_response_type(responses, operation_id)

        path_params = []
        query_params = []
        header_params = []

        for param in parameters:
            param_name = param.get('name', '')
            param_in = param.get('in', '')
            param_required = param.get('required', False)
            param_schema = param.get('schema', {})
            param_type = get_kotlin_type(param_schema)

            param_info = {
                'name': param_name,
                'sanitized_name': sanitize_param_name(param_name),
                'type': param_type,
                'required': param_required,
                'description': param.get('description', '')
            }

            if param_in == 'path':
                path_params.append(param_info)
            elif param_in == 'query':
                query_params.append(param_info)
            elif param_in == 'header':
                header_params.append(param_info)

        func_params = []
        form_params = []  # Track form parameters separately
        has_body_param = False
        has_formdata_param = False

        for p in path_params:
            func_params.append(f"{p['sanitized_name']}: {p['type']}")

        for p in query_params:
            if p['required']:
                func_params.append(f"{p['sanitized_name']}: {p['type']}")
            else:
                func_params.append(f"{p['sanitized_name']}: {p['type']}? = null")

        for p in header_params:
            if not p['required']:
                func_params.append(f"{p['sanitized_name']}: {p['type']}? = null")
            else:
                func_params.append(f"{p['sanitized_name']}: {p['type']}")

        if request_body:
            content = request_body.get('content', {})
            if 'application/json' in content:
                schema = content['application/json'].get('schema', {})
                if '$ref' in schema:
                    body_type = resolve_schema_ref(schema['$ref'])
                    func_params.append(f"body: {body_type}")
                    has_body_param = True
                elif schema.get('type') == 'object':
                    func_params.append("body: Map<String, Any>")
                    has_body_param = True
                elif schema.get('type') == 'string':
                    func_params.append("body: String")
                    has_body_param = True
            elif 'application/x-www-form-urlencoded' in content:
                schema = content['application/x-www-form-urlencoded'].get('schema', {})
                properties = schema.get('properties', {})
                if properties:
                    for prop_name, prop_schema in properties.items():
                        prop_type = get_kotlin_type(prop_schema)
                        prop_required = prop_name in schema.get('required', [])
                        sanitized_prop = sanitize_param_name(prop_name)
                        form_params.append({
                            'name': prop_name,
                            'sanitized_name': sanitized_prop,
                            'required': prop_required,
                            'type': prop_type
                        })
                        if prop_required:
                            func_params.append(f"{sanitized_prop}: {prop_type}")
                        else:
                            func_params.append(f"{sanitized_prop}: {prop_type}? = null")
                else:
                    func_params.append("formData: Map<String, String>")
                    has_formdata_param = True
            elif 'text/plain' in content:
                func_params.append("body: String")
                has_body_param = True

        params_str = ', '.join(func_params)

        if summary:
            service_code += f'\n    /**\n     * {summary}\n'
            if description and description != summary:
                desc_lines = description.strip().split('\n')
                for line in desc_lines[:2]:
                    if line.strip():
                        service_code += f'     * {line.strip()}\n'
            service_code += '     */\n'

        service_code += f'    suspend fun {func_name}({params_str}): Result<{response_type}> {{\n'
        service_code += '        return try {\n'

        # Build path with path parameters
        url_path = path
        for p in path_params:
            url_path = url_path.replace('{' + p['name'] + '}', '${' + p['sanitized_name'] + '}')

        # Remove leading slash for BaseApiClient
        if url_path.startswith('/'):
            url_path = url_path[1:]

        http_method = method.lower()

        # Build query parameters map
        if query_params:
            service_code += '            val queryParams = mapOf(\n'
            for idx, p in enumerate(query_params):
                comma = ',' if idx < len(query_params) - 1 else ''
                if p['required']:
                    service_code += f'                "{p["name"]}" to {p["sanitized_name"]}{comma}\n'
                else:
                    service_code += f'                "{p["name"]}" to {p["sanitized_name"]}{comma}\n'
            service_code += '            ).filterValues { it != null }\n'

        # Build form body map if form parameters exist
        if form_params:
            service_code += '            val formBody = mapOf(\n'
            for idx, p in enumerate(form_params):
                comma = ',' if idx < len(form_params) - 1 else ''
                service_code += f'                "{p["name"]}" to {p["sanitized_name"]}{comma}\n'
            service_code += '            ).filterValues { it != null }\n'

        # Generate appropriate safe method call
        if http_method == 'get':
            if query_params:
                service_code += f'            val result: {response_type} = apiClient.safeGet("{url_path}", queryParams)\n'
            else:
                service_code += f'            val result: {response_type} = apiClient.safeGet("{url_path}")\n'
        elif http_method == 'post':
            # POST with query params and body
            if query_params and (form_params or has_formdata_param or has_body_param):
                if form_params:
                    service_code += f'            val result: {response_type} = apiClient.safePost("{url_path}", queryParams, formBody)\n'
                elif has_formdata_param:
                    service_code += f'            val result: {response_type} = apiClient.safePost("{url_path}", queryParams, formData)\n'
                elif has_body_param:
                    service_code += f'            val result: {response_type} = apiClient.safePost("{url_path}", queryParams, body)\n'
            # POST with only query params
            elif query_params:
                service_code += f'            val result: {response_type} = apiClient.safePost("{url_path}", queryParams)\n'
            # POST with only body
            elif form_params:
                service_code += f'            val result: {response_type} = apiClient.safePost("{url_path}", body = formBody)\n'
            elif has_formdata_param:
                service_code += f'            val result: {response_type} = apiClient.safePost("{url_path}", body = formData)\n'
            elif has_body_param:
                service_code += f'            val result: {response_type} = apiClient.safePost("{url_path}", body = body)\n'
            # POST with no params
            else:
                service_code += f'            val result: {response_type} = apiClient.safePost("{url_path}")\n'
        elif http_method == 'put':
            # PUT with query params and body
            if query_params and (form_params or has_formdata_param or has_body_param):
                if form_params:
                    service_code += f'            val result: {response_type} = apiClient.safePut("{url_path}", queryParams, formBody)\n'
                elif has_formdata_param:
                    service_code += f'            val result: {response_type} = apiClient.safePut("{url_path}", queryParams, formData)\n'
                elif has_body_param:
                    service_code += f'            val result: {response_type} = apiClient.safePut("{url_path}", queryParams, body)\n'
            # PUT with only query params
            elif query_params:
                service_code += f'            val result: {response_type} = apiClient.safePut("{url_path}", queryParams)\n'
            # PUT with only body
            elif form_params:
                service_code += f'            val result: {response_type} = apiClient.safePut("{url_path}", body = formBody)\n'
            elif has_formdata_param:
                service_code += f'            val result: {response_type} = apiClient.safePut("{url_path}", body = formData)\n'
            elif has_body_param:
                service_code += f'            val result: {response_type} = apiClient.safePut("{url_path}", body = body)\n'
            # PUT with no params
            else:
                service_code += f'            val result: {response_type} = apiClient.safePut("{url_path}")\n'
        elif http_method == 'delete':
            if query_params:
                service_code += f'            val result: {response_type} = apiClient.safeDelete("{url_path}", queryParams)\n'
            else:
                service_code += f'            val result: {response_type} = apiClient.safeDelete("{url_path}")\n'

        service_code += '            Result.success(result)\n'
        service_code += '        } catch (e: Exception) {\n'
        service_code += '            Result.failure(e)\n'
        service_code += '        }\n'
        service_code += '    }\n'

    service_code += '}\n'

    return {
        'tag': tag_name,
        'sanitized_tag': sanitized_tag,
        'code': service_code,
        'endpoint_count': len(endpoints)
    }

def write_service_file(service_data):
    """Write service code to file"""
    sanitized_tag = service_data['sanitized_tag']
    output_file = f'library/src/commonMain/kotlin/com/viplearner/api/services/{sanitized_tag}Service.kt'

    # Create directory if it doesn't exist
    os.makedirs(os.path.dirname(output_file), exist_ok=True)

    with open(output_file, 'w') as f:
        f.write(service_data['code'])

    return output_file

def main():
    """Main function to generate services"""
    print('🚀 Lichess API Service Generator')
    print('=' * 60)

    # Get command line arguments
    if len(sys.argv) > 1:
        # Specific tags provided
        tags_to_generate = sys.argv[1:]
        print(f'\n🔨 Generating services for {len(tags_to_generate)} specified tag(s)...\n')
    else:
        # Automatically generate all tags
        tags_to_generate = get_all_tags()
        print(f'\n📋 Found {len(tags_to_generate)} tags in OpenAPI spec')
        print(f'Tags: {", ".join(tags_to_generate)}')
        print(f'\n🔨 Generating services for all {len(tags_to_generate)} tag(s)...\n')

    results = []
    for tag in tags_to_generate:
        print(f'   Processing: {tag}...', end=' ')
        service_data = generate_service_for_tag(tag)

        if service_data:
            output_file = write_service_file(service_data)
            results.append(service_data)
            print(f'✅ {service_data["endpoint_count"]} endpoints')
        else:
            print(f'⚠️  No endpoints')

    print('\n' + '=' * 60)
    print(f'✨ Successfully generated {len(results)} service(s)\n')

    for result in results:
        print(f'   ✓ {result["sanitized_tag"]}Service.kt - {result["endpoint_count"]} endpoints')

    print('\n📁 Location: library/src/commonMain/kotlin/com/viplearner/api/services/')
    print('=' * 60)

if __name__ == '__main__':
    main()

