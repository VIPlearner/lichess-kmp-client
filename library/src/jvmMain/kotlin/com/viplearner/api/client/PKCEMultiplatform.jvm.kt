package com.viplearner.api.client

actual object PKCEMultiplatform {
    actual fun sha256(input: ByteArray): ByteArray {
        val digest = java.security.MessageDigest.getInstance("SHA-256")
        return digest.digest(input)
    }

    actual fun base64UrlEncode(input: ByteArray): String {
        return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(input)
    }
}
