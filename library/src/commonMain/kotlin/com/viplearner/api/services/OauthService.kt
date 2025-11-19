package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for OAuth API endpoints
 * Provides methods to interact with Lichess oauth data
 */
class OauthService(
    private val apiClient: BaseApiClient
) {

    /**
     * Request authorization code
     * OAuth2 authorization endpoint.
     * Start the OAuth2 Authorization Code Flow with PKCE by securely
     */
    suspend fun oauth(response_type: String, client_id: String, redirect_uri: String, code_challenge_method: String, code_challenge: String, scope: String? = null, username: String? = null, state: String? = null): Result<Unit> {
        return try {
            val queryParams = mapOf(
                "response_type" to response_type,
                "client_id" to client_id,
                "redirect_uri" to redirect_uri,
                "code_challenge_method" to code_challenge_method,
                "code_challenge" to code_challenge,
                "scope" to scope,
                "username" to username,
                "state" to state
            ).filterValues { it != null }
            val result: Unit = apiClient.safeGet("oauth", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Obtain access token
     * OAuth2 token endpoint. Exchanges an authorization code for an access token.
     */
    suspend fun token(grant_type: String? = null, code: String? = null, code_verifier: String? = null, redirect_uri: String? = null, client_id: String? = null): Result<TokenResult> {
        return try {
            val formBody = mapOf(
                "grant_type" to grant_type,
                "code" to code,
                "code_verifier" to code_verifier,
                "redirect_uri" to redirect_uri,
                "client_id" to client_id
            ).filterValues { it != null }
            val result: TokenResult = apiClient.safePost("api/token", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Revoke access token
     * Revokes the access token sent as Bearer for this request.
     */
    suspend fun tokenDelete(): Result<Unit> {
        return try {
            val result: Unit = apiClient.safeDelete("api/token")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Test multiple OAuth tokens
     * For up to 1000 OAuth tokens,
     * returns their associated user ID and scopes,
     */
    suspend fun tokenTest(body: String): Result<Map<String, Any>> {
        return try {
            val result: Map<String, Any> = apiClient.safePost("api/token/test", body = body)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
