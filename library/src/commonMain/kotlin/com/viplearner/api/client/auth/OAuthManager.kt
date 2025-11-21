package com.viplearner.api.client.auth

interface OAuthManager {
    suspend fun obtainToken(clientId: String): TokenInfo

    fun close() {}
}
