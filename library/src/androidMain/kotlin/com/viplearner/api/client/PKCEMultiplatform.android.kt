package com.viplearner.api.client

import android.os.Build
import java.security.MessageDigest
import java.util.Base64

actual object PKCEMultiplatform {
    actual fun sha256(input: ByteArray): ByteArray {
        return MessageDigest.getInstance("SHA-256").digest(input)
    }

    actual fun base64UrlEncode(input: ByteArray): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(input)
        } else {
            android.util.Base64.encodeToString(
                input,
                android.util.Base64.URL_SAFE or android.util.Base64.NO_PADDING or android.util.Base64.NO_WRAP,
            )
        }
    }
}
