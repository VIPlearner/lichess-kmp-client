package com.viplearner.api.client

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class LichessClientTest {
    @Test
    fun testFromTokenCreatesClient() {
        val token = "test_token_12345"
        val client = LichessClient.fromToken(token)

        assertNotNull(client)
        assertEquals(token, client.accessToken)
        assertNotNull(client.oauthService)
    }

    @Test
    fun testFromClientIdCreatesClient() {
        val clientId = "my_client_id"
        val client = LichessClient.fromClientId(clientId)

        assertNotNull(client)
        assertEquals(clientId, client.clientId)
        assertNotNull(client.oauthService)
    }

    @Test
    fun testFromClientIdGeneratesRandomId() {
        val client = LichessClient.fromClientId()

        assertNotNull(client)
        assertNotNull(client.clientId)
        assertTrue(client.clientId!!.length == 32)
    }

    @Test
    fun testStartOAuthFlowGeneratesAuthUrl() {
        val client = LichessClient.fromClientId("test_client")
        val redirectUri = "http://localhost:8080/callback"
        val scopes = listOf("board:play", "puzzle:read")

        val (authUrl, flowState) =
            client.startOAuthFlow(
                redirectUri = redirectUri,
                scopes = scopes,
                state = "test_state",
            )

        // Verify auth URL contains required parameters
        assertTrue(authUrl.contains("response_type=code"))
        assertTrue(authUrl.contains("client_id=test_client"))
        assertTrue(authUrl.contains("redirect_uri=http"))
        assertTrue(authUrl.contains("code_challenge_method=S256"))
        assertTrue(authUrl.contains("code_challenge="))
        assertTrue(authUrl.contains("scope=board"))
        assertTrue(authUrl.contains("state=test_state"))

        // Verify flow state
        assertEquals("test_client", flowState.clientId)
        assertNotNull(flowState.codeVerifier)
        assertNotNull(flowState.codeChallenge)
        assertEquals("test_state", flowState.state)
    }

    @Test
    fun testStartOAuthFlowWithoutScopes() {
        val client = LichessClient.fromClientId("test_client")
        val (authUrl, _) =
            client.startOAuthFlow(
                redirectUri = "http://localhost:8080/callback",
            )

        assertNotNull(authUrl)
        assertTrue(authUrl.contains("client_id=test_client"))
    }
}
