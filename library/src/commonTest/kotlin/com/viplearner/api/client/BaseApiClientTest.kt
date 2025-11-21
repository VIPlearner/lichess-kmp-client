package com.viplearner.api.client

import com.viplearner.api.client.auth.NoAuthProvider
import com.viplearner.api.client.auth.StaticTokenAuthProvider
import com.viplearner.api.client.clientid.InMemoryClientIdProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class BaseApiClientTest {
    @Test
    fun testAppliesAuthHeader() =
        runTest {
            val mockClient =
                HttpClient(MockEngine) {
                    engine {
                        addHandler { request ->
                            val authHeader = request.headers[HttpHeaders.Authorization]
                            assertEquals("Bearer test_token", authHeader)

                            respond(
                                """{"success": true}""",
                                HttpStatusCode.OK,
                                headersOf(HttpHeaders.ContentType, "application/json"),
                            )
                        }
                    }
                    install(ContentNegotiation) {
                        json(Json { ignoreUnknownKeys = true })
                    }
                }

            val context =
                ApiContext(
                    baseUrl = "https://test.lichess.org",
                    httpClient = mockClient,
                    authProvider = StaticTokenAuthProvider("test_token"),
                )

            val client = BaseApiClient(context)
            val result: String = client.safeGet("test/endpoint")

            assertNotNull(result)
        }

    @Test
    fun testAppliesClientIdHeader() =
        runTest {
            val mockClient =
                HttpClient(MockEngine) {
                    engine {
                        addHandler { request ->
                            val clientIdHeader = request.headers["X-Client-Id"]
                            assertNotNull(clientIdHeader)
                            assertTrue(clientIdHeader.length == 36) // UUID length

                            respond(
                                """{"success": true}""",
                                HttpStatusCode.OK,
                                headersOf(HttpHeaders.ContentType, "application/json"),
                            )
                        }
                    }
                    install(ContentNegotiation) {
                        json(Json { ignoreUnknownKeys = true })
                    }
                }

            val context =
                ApiContext(
                    baseUrl = "https://test.lichess.org",
                    httpClient = mockClient,
                    authProvider = NoAuthProvider(),
                    clientIdProvider = InMemoryClientIdProvider(),
                )

            val client = BaseApiClient(context)
            val result: String = client.safeGet("test/endpoint")

            assertNotNull(result)
        }

    @Test
    fun testSafeGetBuildsCorrectUrl() =
        runTest {
            val mockClient =
                HttpClient(MockEngine) {
                    engine {
                        addHandler { request ->
                            assertEquals("https://test.lichess.org/api/test/endpoint?key=value", request.url.toString())

                            respond(
                                """{"result": "ok"}""",
                                HttpStatusCode.OK,
                                headersOf(HttpHeaders.ContentType, "application/json"),
                            )
                        }
                    }
                    install(ContentNegotiation) {
                        json(Json { ignoreUnknownKeys = true })
                    }
                }

            val context =
                ApiContext(
                    baseUrl = "https://test.lichess.org/api",
                    httpClient = mockClient,
                    authProvider = NoAuthProvider(),
                )

            val client = BaseApiClient(context)
            val result: Map<String, String> = client.safeGet("test/endpoint", mapOf("key" to "value"))

            assertEquals("ok", result["result"])
        }

    @Test
    fun testHandlesErrorResponses() =
        runTest {
            val mockClient =
                HttpClient(MockEngine) {
                    engine {
                        addHandler { request ->
                            respond(
                                """{"error": "Not found"}""",
                                HttpStatusCode.NotFound,
                                headersOf(HttpHeaders.ContentType, "application/json"),
                            )
                        }
                    }
                    install(ContentNegotiation) {
                        json(Json { ignoreUnknownKeys = true })
                    }
                }

            val context =
                ApiContext(
                    baseUrl = "https://test.lichess.org",
                    httpClient = mockClient,
                    authProvider = NoAuthProvider(),
                )

            val client = BaseApiClient(context)

            var exceptionThrown = false
            try {
                client.safeGet<Map<String, Any>>("test/endpoint")
            } catch (e: Exception) {
                exceptionThrown = true
                assertTrue(e.message?.contains("API request failed") == true)
            }

            assertTrue(exceptionThrown)
        }
}
