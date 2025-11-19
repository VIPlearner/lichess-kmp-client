package com.viplearner.api.client

import kotlinx.cinterop.*
import platform.Foundation.*
import platform.CoreCrypto.*
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
    return this.usePinned { pinned ->
        NSData.create(
            bytes = pinned.addressOf(0),
            length = this.size.toULong()
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun NSData.toByteArray(): ByteArray {
    return ByteArray(this.length.toInt()).apply {
        usePinned {
            memcpy(it.addressOf(0), this@toByteArray.bytes, this@toByteArray.length)
        }
    }
}