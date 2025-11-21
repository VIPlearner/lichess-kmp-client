package com.viplearner.api.client.auth

interface AuthProvider {
    suspend fun authHeader(): String?

    fun close() {}
}

class NoAuthProvider : AuthProvider {
    override suspend fun authHeader(): String? = null
}

class StaticTokenAuthProvider(private val token: String) : AuthProvider {
    override suspend fun authHeader(): String = "Bearer $token"
}
