package com.viplearner.api.models

data class TokenResult(
    val token_type: String,
    val access_token: String,
    val expires_in: Int
)
