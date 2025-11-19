package com.viplearner.api.client

import com.viplearner.lichess.client.core.ApiException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


val LICHESS_API_URL = "https://lichess.org/api"
/**
 * Base API client for making HTTP requests with Ktor.
 *
 * @property baseUrl The base URL for all API requests
 * @property token The bearer token for authentication
 */
class BaseApiClient(
    val baseUrl: String,
    token: String
) {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
                explicitNulls = false
            })
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
        query: Map<String, Any?> = emptyMap()
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
        body: Any? = null
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
        body: Any? = null
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
        query: Map<String, Any?> = emptyMap()
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
    suspend inline fun <reified T> makeRequest(
        block: () -> HttpResponse
    ): T {
        val response = block()

        return if (response.status.isSuccess()) {
            response.body()
        } else {
            val errorBody = response.bodyAsText()
            throw ApiException(
                message = "API request failed",
                status = response.status.value,
                body = errorBody
            )
        }
    }

    /**
     * Closes the HTTP client and releases resources.
     */
    fun close() {
        client.close()
    }
}

