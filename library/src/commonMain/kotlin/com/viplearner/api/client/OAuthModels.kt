package com.viplearner.api.client

import kotlinx.serialization.Serializable

/**
 * Data class representing the OAuth authorization flow state.
 * This should be persisted by the user to complete the OAuth flow.
 */
@Serializable
data class OAuthFlowState(
    val clientId: String,
    val codeVerifier: String,
    val codeChallenge: String,
    val state: String? = null
)

/**
 * Data class representing an OAuth access token response.
 */
@Serializable
data class OAuthToken(
    val access_token: String,
    val token_type: String,
    val expires_in: Int? = null
)

