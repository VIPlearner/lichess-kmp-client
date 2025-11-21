package com.viplearner.api.client.auth

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Clock.System.now
import kotlin.time.ExperimentalTime

class OAuthAuthProvider(
    private val clientId: String,
    private val oauthManager: OAuthManager,
) : AuthProvider {
    private val mutex = Mutex()
    private var cachedToken: TokenInfo? = null

    override suspend fun authHeader(): String? {
        mutex.withLock {
            val token = cachedToken
            if (token == null || isExpired(token)) {
                cachedToken = oauthManager.obtainToken(clientId)
            }
        }
        return cachedToken?.let { "Bearer ${it.accessToken}" }
    }

    @OptIn(ExperimentalTime::class)
    private fun isExpired(token: TokenInfo): Boolean {
        val expiresAt = token.expiresAt ?: return false
        return now().epochSeconds >= expiresAt
    }

    override fun close() {
        oauthManager.close()
    }
}
