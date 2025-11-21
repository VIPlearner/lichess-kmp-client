package com.viplearner.api.client.auth

data class TokenInfo(
    val accessToken: String,
    val expiresAt: Long? = null,
)
