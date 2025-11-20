package com.viplearner.api.client

import android.util.Base64
import java.security.MessageDigest

actual object PKCEMultiplatform {
    actual fun sha256(input: ByteArray): ByteArray {
        return MessageDigest.getInstance("SHA-256").digest(input)
    }

    actual fun base64UrlEncode(input: ByteArray): String {
        return Base64.encodeToString(input, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
    }
}
