package com.viplearner.api.client

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.usePinned
import platform.CoreCrypto.CC_SHA256
import platform.CoreCrypto.CC_SHA256_DIGEST_LENGTH
import platform.Foundation.NSData
import platform.Foundation.NSMutableData
import platform.Foundation.base64EncodedStringWithOptions
import platform.Foundation.create
import platform.Foundation.dataWithLength
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual object PKCEMultiplatform {
    actual fun sha256(input: ByteArray): ByteArray {
        val data = input.toNSData()
        val hash = NSMutableData.dataWithLength(CC_SHA256_DIGEST_LENGTH.toULong())!!
        CC_SHA256(data.bytes, data.length.toUInt(), hash.mutableBytes?.reinterpret())
        return hash.toByteArray()
    }

    actual fun base64UrlEncode(input: ByteArray): String {
        val data = input.toNSData()
        val base64 = data.base64EncodedStringWithOptions(0u)
        // Convert to URL-safe base64
        return base64
            .replace("+", "-")
            .replace("/", "_")
            .replace("=", "")
    }
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
private fun ByteArray.toNSData(): NSData {
    if (this.isEmpty()) {
        return NSData.create(bytes = null, length = 0u)
    }
    return this.usePinned { pinned ->
        NSData.create(
            bytes = pinned.addressOf(0),
            length = this.size.toULong(),
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun NSData.toByteArray(): ByteArray {
    val length = this.length.toInt()
    if (length == 0) {
        return ByteArray(0)
    }
    return ByteArray(length).apply {
        usePinned {
            memcpy(it.addressOf(0), this@toByteArray.bytes, this@toByteArray.length)
        }
    }
}
