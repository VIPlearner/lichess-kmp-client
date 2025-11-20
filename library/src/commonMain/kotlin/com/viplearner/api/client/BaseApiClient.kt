package com.viplearner.api.client

import com.viplearner.lichess.client.core.ApiException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

const val LICHESS_API_URL = "https://lichess.org/api"

/**
 * Base API client for making HTTP requests with Ktor.
 *
 * @property baseUrl The base URL for all API requests
 * @property token The bearer token for authentication
 * @property httpClient Optional custom HttpClient (useful for testing with MockEngine)
 */
class BaseApiClient(
    val baseUrl: String,
    token: String,
    httpClient: HttpClient? = null,
) {
    val client: HttpClient = httpClient ?: createDefaultHttpClient(token)

    companion object {
        val json =
            Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
                explicitNulls = false
            }

        /**
         * Creates a default HttpClient with standard configuration.
         * This is exposed so tests can create clients with similar configuration.
         */
        fun createDefaultHttpClient(token: String): HttpClient {
            return HttpClient {
                engine {
                    dispatcher = Dispatchers.IO
                    pipelining = true
                }
                install(ContentNegotiation) {
                    json(json)
                }

                install(Auth) {
                    bearer {
                        loadTokens {
                            BearerTokens(token, null)
                        }
                    }
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

        /**
         * Creates a test-friendly HttpClient with minimal configuration.
         * Useful for creating mock clients without auth, timeouts, etc.
         */
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

    /**
     * Makes a safe GET request and deserializes the response to type T.
     *
     * @param path The path to append to the base URL
     * @param query Query parameters to include in the request
     * @return The deserialized response
     * @throws com.viplearner.lichess.client.core.ApiException if the request fails
     */
    suspend inline fun <reified T> safeGet(
        path: String,
        query: Map<String, Any?> = emptyMap(),
    ): T {
        return makeRequest {
            client.get {
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

    /**
     * Makes a safe GET request for PGN content and returns it as a String.
     *
     * @param path The path to append to the base URL
     * @param query Query parameters to include in the request
     * @return The PGN content as a String
     * @throws com.viplearner.lichess.client.core.ApiException if the request fails
     */
    suspend fun safeGetPgn(
        path: String,
        query: Map<String, Any?> = emptyMap(),
    ): String {
        val response =
            client.get {
                headers {
                    append(HttpHeaders.Accept, "application/x-chess-pgn")
                }
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

    /**
     * Makes a safe POST request and deserializes the response to type T.
     *
     * @param path The path to append to the base URL
     * @param query Query parameters to include in the request
     * @param body The request body to send
     * @return The deserialized response
     * @throws com.viplearner.lichess.client.core.ApiException if the request fails
     */
    suspend inline fun <reified T> safePost(
        path: String,
        query: Map<String, Any?> = emptyMap(),
        body: Any? = null,
    ): T {
        return makeRequest {
            client.post {
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

    /**
     * Makes a safe PUT request and deserializes the response to type T.
     *
     * @param path The path to append to the base URL
     *
     * @param body The request body to send
     * @return The deserialized response
     * @throws com.viplearner.lichess.client.core.ApiException if the request fails
     */
    suspend inline fun <reified T> safePut(
        path: String,
        query: Map<String, Any?> = emptyMap(),
        body: Any? = null,
    ): T {
        return makeRequest {
            client.put {
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

    /**
     * Makes a safe DELETE request and deserializes the response to type T.
     *
     * @param path The path to append to the base URL
     * @return The deserialized response
     * @throws com.viplearner.lichess.client.core.ApiException if the request fails
     */
    suspend inline fun <reified T> safeDelete(
        path: String,
        query: Map<String, Any?> = emptyMap(),
    ): T {
        return makeRequest {
            client.delete {
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

    /**
     * Helper function to make a request and handle errors.
     *
     * @param block The request to execute
     * @return The deserialized response
     * @throws com.viplearner.lichess.client.core.ApiException if the request fails
     */
    suspend inline fun <reified T> makeRequest(block: () -> HttpResponse): T {
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

    /**
     * Makes a safe POST request with form-urlencoded data and deserializes the response to type T.
     *
     * @param path The path to append to the base URL
     * @param query Query parameters to include in the request
     * @param formData The form data to send as application/x-www-form-urlencoded
     * @return The deserialized response
     * @throws com.viplearner.lichess.client.core.ApiException if the request fails
     */
    suspend inline fun <reified T> safePostFormUrlEncoded(
        path: String,
        query: Map<String, Any?> = emptyMap(),
        formData: Map<String, String>,
    ): T {
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
            )
        }
    }

    /**
     * Makes a safe POST request with form-urlencoded data and returns an NDJSON stream.
     *
     * @param path The path to append to the base URL
     * @param query Query parameters to include in the request
     * @param formData The form data to send as application/x-www-form-urlencoded
     * @return A flow of strings (lines from the NDJSON stream)
     * @throws com.viplearner.lichess.client.core.ApiException if the request fails
     */
    fun safePostFormUrlEncodedNdjson(
        path: String,
        query: Map<String, Any?> = emptyMap(),
        formData: Map<String, String>,
    ): Flow<String> {
        return flow {
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

    /**
     * Helper function for streaming ndJson responses.
     */
    suspend inline fun <reified T> safeNdjsonGet(
        path: String,
        query: Map<String, Any?> = emptyMap(),
    ): Flow<T> {
        return makeNdJsonRequest {
            client.get {
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

    /**
     * Helper function for streaming ndJson responses.
     */
    suspend inline fun <reified T> safeNdjsonPost(
        path: String,
        query: Map<String, Any?> = emptyMap(),
        body: Any? = null,
    ): Flow<T> {
        return makeNdJsonRequest {
            client.post {
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

    /**
     * Helper function to make a ndJson request and handle errors.
     *
     * @param block The request to execute
     * @return A flow of deserialized responses
     * @throws com.viplearner.lichess.client.core.ApiException if the request fails
     */
    inline fun <reified T> makeNdJsonRequest(crossinline block: suspend () -> HttpResponse): Flow<T> {
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

    /**
     * Closes the HTTP client and releases resources.
     */
    fun close() {
        client.close()
    }
}
