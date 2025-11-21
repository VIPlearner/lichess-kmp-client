package com.viplearner.api.client.examples

import com.viplearner.api.client.LichessClient
import com.viplearner.api.client.auth.DefaultOAuthManager

/**
 * Example usage patterns for the Lichess API client
 */
object LichessClientExamples {
    /**
     * Example 1: Anonymous client (no authentication)
     */
    suspend fun anonymousClient() {
        val client = LichessClient.anonymous()

        client.tv.tvFeed()

        client.close()
    }

    /**
     * Example 2: Authenticated client with static token
     */
    suspend fun authenticatedClient() {
        val client = LichessClient.withToken("lip_myAccessToken123")

        // Access authenticated endpoints
        val games = client.games
        // val myGames = games.listUserGames("myUsername")

        client.close()
    }

    /**
     * Example 3: Client with custom client ID for tracking
     */
    suspend fun clientWithClientId() {
        val client = LichessClient.withClientId("my-app-v1.0")

        // All requests will include X-Client-Id header
        client.users.usersStatus("user1,user2,user3")

        client.close()
    }

    /**
     * Example 4: OAuth-enabled client with automatic token refresh
     */
    suspend fun oauthClient() {
        val oauthManager =
            DefaultOAuthManager(
                redirectUri = "https://myapp.com/oauth/callback",
                scopes = listOf("board:play", "puzzle:read"),
                onAuthorizationRequired = { authUrl, codeVerifier ->
                    // 1. Direct user to authUrl
                    println("Please visit: $authUrl")

                    // 2. User authorizes and returns with code
                    // 3. Return the authorization code
                    "authorization_code_from_callback"
                },
            )

        val client =
            LichessClient.withClientIdAndOAuth(
                oauthManager = oauthManager,
                clientId = "my-app-v1.0",
            )

        client.account.account()

        client.close()
    }
}
