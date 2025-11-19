package com.viplearner.api.client

object PKCEUtil {
    fun generateCodeVerifier(length: Int = 64): String {
        require(length in 43..128) { "PKCE code_verifier must be between 43 and 128 characters" }
        val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-._~"
        return (1..length).map { chars.random() }.joinToString("")
    }

    fun generateCodeChallenge(codeVerifier: String): String {
        val bytes = codeVerifier.encodeToByteArray()
        val digest = PKCEMultiplatform.sha256(bytes)
        return PKCEMultiplatform.base64UrlEncode(digest)
    }
}
