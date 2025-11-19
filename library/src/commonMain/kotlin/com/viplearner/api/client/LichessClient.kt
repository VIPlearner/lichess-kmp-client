package com.viplearner.api.client

import com.viplearner.api.services.OauthService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Main entry point for interacting with Lichess API services.
 *
 * Initialize with either an access token (if you already have one),
 * or with a client_id to perform the OAuth PKCE flow.
 */
class LichessClient private constructor(
    private val httpClient: HttpClient,
    val accessToken: String? = null,
    val clientId: String? = null
) {
    // Expose all service objects here
    val oauthService: OauthService = OauthService(BaseApiClient(LICHESS_API_URL, ""))
    // TODO: Add other services as properties

    /**
     * Starts the OAuth PKCE flow by generating the authorization URL.
     *
     * @param redirectUri The URI to redirect to after authorization
     * @param scopes List of OAuth scopes to request (e.g., "board:play", "puzzle:read")
     * @param state Optional state parameter for CSRF protection
     * @return OAuthFlowState containing the authorization URL and state to persist
     */
    fun startOAuthFlow(
        redirectUri: String,
        scopes: List<String> = emptyList(),
        state: String? = null
    ): Pair<String, OAuthFlowState> {
        require(clientId != null) { "Client ID is required for OAuth flow. Use fromClientId() to initialize." }

        val codeVerifier = PKCEUtil.generateCodeVerifier()
        val codeChallenge = PKCEUtil.generateCodeChallenge(codeVerifier)
        val flowState = OAuthFlowState(
            clientId = clientId,
            codeVerifier = codeVerifier,
            codeChallenge = codeChallenge,
            state = state
        )

        val authUrl = buildString {
            append("https://lichess.org/oauth")
            append("?response_type=code")
            append("&client_id=$clientId")
            append("&redirect_uri=${redirectUri.encodeURLParameter()}")
            append("&code_challenge_method=S256")
            append("&code_challenge=$codeChallenge")
            if (scopes.isNotEmpty()) {
                append("&scope=${scopes.joinToString(" ").encodeURLParameter()}")
            }
            if (state != null) {
                append("&state=${state.encodeURLParameter()}")
            }
        }

        return authUrl to flowState
    }

    /**
     * Exchanges an authorization code for an access token.
     *
     * @param code The authorization code received from the callback
     * @param flowState The OAuthFlowState from startOAuthFlow
     * @param redirectUri The same redirect URI used in startOAuthFlow
     * @return LichessClient instance with the access token set
     */
    suspend fun exchangeCodeForToken(
        code: String,
        flowState: OAuthFlowState,
        redirectUri: String
    ): Result<LichessClient> {
        return try {
            val client = HttpClient {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    })
                }
            }

            val response = client.post("https://lichess.org/api/token") {
                contentType(ContentType.Application.FormUrlEncoded)
                setBody(Parameters.build {
                    append("grant_type", "authorization_code")
                    append("code", code)
                    append("code_verifier", flowState.codeVerifier)
                    append("redirect_uri", redirectUri)
                    append("client_id", flowState.clientId)
                })
            }

            val tokenResponse = response.body<OAuthToken>()
            client.close()

            Result.success(fromToken(tokenResponse.access_token))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        /**
         * Initialize with an access token (user already has a token)
         */
        fun fromToken(token: String): LichessClient {
            val client = BaseApiClient(LICHESS_API_URL, token).client
            return LichessClient(client, accessToken = token)
        }

        /**
         * Initialize with a client_id (user will perform OAuth PKCE flow)
         * If client_id is null, a random one is generated.
         */
        fun fromClientId(clientId: String? = null): LichessClient {
            val id = clientId ?: generateRandomClientId()
            val client = HttpClient {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    })
                }
            }
            return LichessClient(client, clientId = id)
        }

        private fun generateRandomClientId(): String {
            val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_"
            return (1..32).map { chars.random() }.joinToString("")
        }
    }
}

