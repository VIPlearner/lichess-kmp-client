package com.viplearner.api.client

import com.viplearner.lichess.client.core.ApiException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.encodeURLParameter
import io.ktor.http.headers
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

const val LICHESS_API_URL = "https://lichess.org"

class BaseApiClient(private val ctx: ApiContext) {
    internal val baseUrl: String = ctx.baseUrl
    internal val client: HttpClient = ctx.httpClient

    companion object {
        val json =
            Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
                explicitNulls = false
            }

        fun createDefaultHttpClient(): HttpClient {
            return HttpClient {
                engine {
                    pipelining = true
                }
                install(ContentNegotiation) {
                    json(json)
                }

                install(HttpTimeout) {
                    requestTimeoutMillis = 30_000
                    connectTimeoutMillis = 30_000
                    socketTimeoutMillis = 30_000
                }

                install(HttpRequestRetry) {
                    maxRetries = 3
                    retryOnServerErrors(maxRetries = 3)
                    exponentialDelay()
                }

                defaultRequest {
                    header(HttpHeaders.Accept, ContentType.Application.Json.toString())
                }
            }
        }

        fun createTestHttpClient(): HttpClient {
            return HttpClient {
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            coerceInputValues = true
                            explicitNulls = false
                        },
                    )
                }
            }
        }
    }

    internal suspend fun applyHeaders(builder: HttpRequestBuilder) {
        ctx.authProvider.authHeader()?.let { authHeader ->
            builder.header(HttpHeaders.Authorization, authHeader)
        }
        ctx.clientIdProvider?.getClientId()?.let { clientId ->
            builder.header("X-Client-Id", clientId)
        }
    }

    internal suspend inline fun <reified T> safeGet(
        path: String,
        query: Map<String, Any?> = emptyMap(),
    ): T {
        return makeRequest {
            client.get {
                applyHeaders(this)
                url {
                    takeFrom(baseUrl)
                    appendPathSegments(path.trim('/'))
                    query.forEach { (key, value) ->
                        value?.let { parameters.append(key, it.toString()) }
                    }
                }
            }
        }
    }

    internal suspend fun safeGetPgn(
        path: String,
        query: Map<String, Any?> = emptyMap(),
    ): String {
        val response =
            client.get {
                applyHeaders(this)
                header(HttpHeaders.Accept, "application/x-chess-pgn")
                url {
                    takeFrom(baseUrl)
                    appendPathSegments(path.trim('/'))
                    query.forEach { (key, value) ->
                        value?.let { parameters.append(key, it.toString()) }
                    }
                }
            }

        return if (response.status.isSuccess()) {
            response.bodyAsText()
        } else {
            val errorBody = response.bodyAsText()
            throw ApiException(
                message = "API request failed",
                status = response.status.value,
                body = errorBody,
            )
        }
    }

    internal suspend inline fun <reified T> safePost(
        path: String,
        query: Map<String, Any?> = emptyMap(),
        body: Any? = null,
    ): T {
        return makeRequest {
            client.post {
                applyHeaders(this)
                url {
                    takeFrom(baseUrl)
                    appendPathSegments(path.trim('/'))
                    query.forEach { (key, value) ->
                        value?.let { parameters.append(key, it.toString()) }
                    }
                }
                if (body != null) {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }
            }
        }
    }

    internal suspend inline fun <reified T> safePut(
        path: String,
        query: Map<String, Any?> = emptyMap(),
        body: Any? = null,
    ): T {
        return makeRequest {
            client.put {
                applyHeaders(this)
                url {
                    takeFrom(baseUrl)
                    appendPathSegments(path.trim('/'))
                    query.forEach { (key, value) ->
                        value?.let { parameters.append(key, it.toString()) }
                    }
                }
                if (body != null) {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }
            }
        }
    }

    internal suspend inline fun <reified T> safeDelete(
        path: String,
        query: Map<String, Any?> = emptyMap(),
    ): T {
        return makeRequest {
            client.delete {
                applyHeaders(this)
                url {
                    takeFrom(baseUrl)
                    appendPathSegments(path.trim('/'))
                    query.forEach { (key, value) ->
                        value?.let { parameters.append(key, it.toString()) }
                    }
                }
            }
        }
    }

    internal suspend inline fun <reified T> makeRequest(block: suspend () -> HttpResponse): T {
        val response = block()

        return if (response.status.isSuccess()) {
            response.body()
        } else {
            val errorBody = response.bodyAsText()
            throw ApiException(
                message = "API request failed",
                status = response.status.value,
                body = errorBody,
            )
        }
    }

    internal suspend inline fun <reified T> safePostFormUrlEncoded(
        path: String,
        query: Map<String, Any?> = emptyMap(),
        formData: Map<String, String>,
    ): T {
        val authHeader = ctx.authProvider.authHeader()
        val clientIdHeader = ctx.clientIdProvider?.getClientId()

        return makeRequest {
            client.submitForm(
                url =
                    buildString {
                        append(baseUrl)
                        append("/")
                        append(path.trim('/'))
                        if (query.isNotEmpty()) {
                            append("?")
                            append(
                                query.entries.joinToString("&") { (key, value) ->
                                    value?.let { "$key=${it.toString().encodeURLParameter()}" } ?: ""
                                },
                            )
                        }
                    },
                formParameters =
                    Parameters.build {
                        formData.forEach { (key, value) ->
                            append(key, value)
                        }
                    },
            ) {
                authHeader?.let { header(HttpHeaders.Authorization, it) }
                clientIdHeader?.let { header("X-Client-Id", it) }
            }
        }
    }

    internal fun safePostFormUrlEncodedNdjson(
        path: String,
        query: Map<String, Any?> = emptyMap(),
        formData: Map<String, String>,
    ): Flow<String> {
        return flow {
            val authHeader = ctx.authProvider.authHeader()
            val clientIdHeader = ctx.clientIdProvider?.getClientId()

            val response =
                client.submitForm(
                    url =
                        buildString {
                            append(baseUrl)
                            append("/")
                            append(path.trim('/'))
                            if (query.isNotEmpty()) {
                                append("?")
                                append(
                                    query.entries.joinToString("&") { (key, value) ->
                                        value?.let { "$key=${it.toString().encodeURLParameter()}" } ?: ""
                                    },
                                )
                            }
                        },
                    formParameters =
                        Parameters.build {
                            formData.forEach { (key, value) ->
                                append(key, value)
                            }
                        },
                ) {
                    authHeader?.let { header(HttpHeaders.Authorization, it) }
                    clientIdHeader?.let { header("X-Client-Id", it) }
                    headers {
                        append("Accept", "application/x-ndjson")
                    }
                }

            if (!response.status.isSuccess()) {
                val errorBody = response.bodyAsText()
                throw ApiException(
                    message = "API request failed",
                    status = response.status.value,
                    body = errorBody,
                )
            }

            val channel: ByteReadChannel = response.body()
            while (!channel.isClosedForRead) {
                val line = channel.readUTF8Line()
                if (line != null) {
                    emit(line)
                }
            }
        }
    }

    internal suspend inline fun <reified T> safeNdjsonGet(
        path: String,
        query: Map<String, Any?> = emptyMap(),
    ): Flow<T> {
        return makeNdJsonRequest {
            client.get {
                applyHeaders(this)
                headers {
                    append("Accept", "application/x-ndjson")
                }
                url {
                    takeFrom(baseUrl)
                    appendPathSegments(path.trim('/'))
                    query.forEach { (key, value) ->
                        value?.let { parameters.append(key, it.toString()) }
                    }
                }
            }
        }
    }

    internal suspend inline fun <reified T> safeNdjsonPost(
        path: String,
        query: Map<String, Any?> = emptyMap(),
        body: Any? = null,
    ): Flow<T> {
        return makeNdJsonRequest {
            client.post {
                applyHeaders(this)
                headers {
                    append("Accept", "application/x-ndjson")
                }
                url {
                    takeFrom(baseUrl)
                    appendPathSegments(path.trim('/'))
                    query.forEach { (key, value) ->
                        value?.let { parameters.append(key, it.toString()) }
                    }
                }
                if (body != null) {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }
            }
        }
    }

    internal inline fun <reified T> makeNdJsonRequest(crossinline block: suspend () -> HttpResponse): Flow<T> {
        return flow {
            val response = block()
            val channel: ByteReadChannel = response.body()

            while (!channel.isClosedForRead) {
                val line = channel.readUTF8Line()
                if (line != null) {
                    emit(json.decodeFromString(line))
                }
            }
        }
    }

    fun close() {
        client.close()
    }
}
