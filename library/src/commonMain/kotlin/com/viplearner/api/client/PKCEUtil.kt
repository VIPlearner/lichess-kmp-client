package com.viplearner.api.client

import korlibs.crypto.SHA256
import korlibs.encoding.Base64

object PKCEUtil {
    fun sha256(input: ByteArray): ByteArray {
        return SHA256.digest(input).bytes
    }

    fun base64UrlEncode(input: ByteArray): String {
        return Base64.encode(input, url = true)
    }

    fun generateCodeVerifier(length: Int = 64): String {
        require(length in 43..128) { "PKCE code_verifier must be between 43 and 128 characters" }
        val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-._~"
        return (1..length).map { chars.random() }.joinToString("")
    }

    fun generateCodeChallenge(codeVerifier: String): String {
        val bytes = codeVerifier.encodeToByteArray()
        val digest = sha256(bytes)
        return base64UrlEncode(digest)
    }
}
