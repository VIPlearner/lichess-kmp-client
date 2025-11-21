package com.viplearner.api.client.auth

import com.viplearner.api.client.OAuthToken
import com.viplearner.api.client.PKCEUtil
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.time.Clock.System.now
import kotlin.time.ExperimentalTime

class DefaultOAuthManager(
    private val redirectUri: String,
    private val scopes: List<String> = emptyList(),
    private val onAuthorizationRequired: suspend (authUrl: String, codeVerifier: String) -> String,
) : OAuthManager {
    private val httpClient =
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    },
                )
            }
        }

    override suspend fun obtainToken(clientId: String): TokenInfo {
        val codeVerifier = PKCEUtil.generateCodeVerifier()
        val codeChallenge = PKCEUtil.generateCodeChallenge(codeVerifier)

        val authUrl = buildAuthorizationUrl(clientId, codeChallenge)
        val authorizationCode = onAuthorizationRequired(authUrl, codeVerifier)

        return exchangeCodeForToken(clientId, authorizationCode, codeVerifier)
    }

    private fun buildAuthorizationUrl(
        clientId: String,
        codeChallenge: String,
    ): String {
        return buildString {
            append("https://lichess.org/oauth")
            append("?response_type=code")
            append("&client_id=$clientId")
            append("&redirect_uri=$redirectUri")
            append("&code_challenge_method=S256")
            append("&code_challenge=$codeChallenge")
            if (scopes.isNotEmpty()) {
                append("&scope=${scopes.joinToString(" ")}")
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    private suspend fun exchangeCodeForToken(
        clientId: String,
        code: String,
        codeVerifier: String,
    ): TokenInfo {
        val response =
            httpClient.post("https://lichess.org/api/token") {
                contentType(ContentType.Application.FormUrlEncoded)
                setBody(
                    Parameters.build {
                        append("grant_type", "authorization_code")
                        append("code", code)
                        append("code_verifier", codeVerifier)
                        append("redirect_uri", redirectUri)
                        append("client_id", clientId)
                    },
                )
            }

        val tokenResponse = response.body<OAuthToken>()
        val expiresAt =
            tokenResponse.expires_in?.let {
                now().epochSeconds + it
            }

        return TokenInfo(
            accessToken = tokenResponse.access_token,
            expiresAt = expiresAt,
        )
    }

    override fun close() {
        httpClient.close()
    }
}
