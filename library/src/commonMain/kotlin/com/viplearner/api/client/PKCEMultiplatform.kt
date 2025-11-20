package com.viplearner.api.client

/**
 * Multiplatform SHA-256 and Base64 utilities for PKCE.
 */
expect object PKCEMultiplatform {
    fun sha256(input: ByteArray): ByteArray

    fun base64UrlEncode(input: ByteArray): String
}
