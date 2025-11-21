package com.viplearner.api.client

import com.viplearner.api.client.auth.OAuthManager
import com.viplearner.api.client.auth.TokenInfo
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
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class LichessClientTest {
    @Test
    fun testAnonymousClient() {
        val client = LichessClient.anonymous()

        assertNotNull(client)
        assertNotNull(client.games)
        assertNotNull(client.users)
        assertNotNull(client.account)

        client.close()
    }

    @Test
    fun testWithTokenClient() {
        val client = LichessClient.withToken("lip_test_token_123")

        assertNotNull(client)
        assertNotNull(client.games)
        assertNotNull(client.users)

        client.close()
    }

    @Test
    fun testWithClientIdGeneratesUUID() {
        val client = LichessClient.withClientId()

        assertNotNull(client)

        client.close()
    }

    @Test
    fun testWithClientIdUsesProvidedId() {
        val customId = "my-custom-client-id"
        val client = LichessClient.withClientId(customId)

        assertNotNull(client)

        client.close()
    }

    @Test
    fun testWithOAuthClient() =
        runTest {
            val mockOAuthManager =
                object : OAuthManager {
                    override suspend fun obtainToken(clientId: String): TokenInfo {
                        return TokenInfo("oauth_access_token", null)
                    }
                }

            val client = LichessClient.withClientIdAndOAuth(mockOAuthManager, "test-client")

            assertNotNull(client)

            client.close()
        }

    @Test
    fun testLazyServiceInitialization() {
        val client = LichessClient.anonymous()

        // Services should be initialized lazily
        val games1 = client.games
        val games2 = client.games

        // Should return same instance
        assertTrue(games1 === games2)

        client.close()
    }

    @Test
    fun testAllServicesAccessible() {
        val client = LichessClient.anonymous()

        // Verify all services are accessible
        assertNotNull(client.games)
        assertNotNull(client.users)
        assertNotNull(client.account)
        assertNotNull(client.board)
        assertNotNull(client.bot)
        assertNotNull(client.challenges)
        assertNotNull(client.analysis)
        assertNotNull(client.arena)
        assertNotNull(client.broadcasts)
        assertNotNull(client.bulkPairings)
        assertNotNull(client.externalEngine)
        assertNotNull(client.fide)
        assertNotNull(client.messaging)
        assertNotNull(client.oauth)
        assertNotNull(client.openingExplorer)
        assertNotNull(client.puzzles)
        assertNotNull(client.relations)
        assertNotNull(client.simuls)
        assertNotNull(client.studies)
        assertNotNull(client.swiss)
        assertNotNull(client.tablebase)
        assertNotNull(client.teams)
        assertNotNull(client.tv)

        client.close()
    }

    @Test
    fun testCloseDoesNotThrow() {
        val client = LichessClient.anonymous()
        client.close() // Should not throw
    }

    @Test
    fun testWithCustomHttpClient() {
        val mockClient =
            HttpClient(MockEngine) {
                engine {
                    addHandler { request ->
                        respond(
                            """{"test": "custom"}""",
                            HttpStatusCode.OK,
                            headersOf(HttpHeaders.ContentType, "application/json"),
                        )
                    }
                }
                install(ContentNegotiation) {
                    json(Json { ignoreUnknownKeys = true })
                }
            }

        val client = LichessClient.anonymous(httpClient = mockClient)

        assertNotNull(client)

        client.close()
        mockClient.close()
    }

    @Test
    fun testWithCustomBaseUrl() {
        val customUrl = "https://custom.lichess.org/api"
        val client = LichessClient.anonymous(baseUrl = customUrl)

        assertNotNull(client)

        client.close()
    }

    @Test
    fun testFactoryMethodsCreateIndependentInstances() {
        val client1 = LichessClient.anonymous()
        val client2 = LichessClient.anonymous()

        // Should be different instances
        assertTrue(client1 !== client2)

        client1.close()
        client2.close()
    }
}
