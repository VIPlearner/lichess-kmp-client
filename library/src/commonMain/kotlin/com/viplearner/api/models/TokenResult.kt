package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class TokenResult(
    val token_type: String,
    val access_token: String,
    val expires_in: Int,
)

@Serializable
data class TokenStatus(
    val userId: String,
    val scopes: String,
    val expires: Long?,
)
