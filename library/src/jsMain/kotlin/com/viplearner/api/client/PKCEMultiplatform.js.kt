package com.viplearner.api.client

/**
 * JavaScript implementation of PKCEMultiplatform using native JS crypto APIs.
 * Works in both browser and Node.js environments.
 */
actual object PKCEMultiplatform {
    actual fun sha256(input: ByteArray): ByteArray {
        // Convert Kotlin ByteArray to JS Uint8Array
        val uint8Array = input.toUint8Array()

        // Use native crypto API
        val hashArray =
            if (isNodeJs()) {
                // Node.js environment
                sha256NodeJs(uint8Array)
            } else {
                // Browser environment - use Web Crypto API synchronously via a workaround
                // Note: This uses SubtleCrypto which is async, so we use a sync alternative
                sha256Browser(uint8Array)
            }

        // Convert back to Kotlin ByteArray
        return jsArrayToByteArray(hashArray)
    }

    actual fun base64UrlEncode(input: ByteArray): String {
        // Convert to base64
        val base64 =
            if (isNodeJs()) {
                base64EncodeNodeJs(input)
            } else {
                base64EncodeBrowser(input)
            }

        // Make it URL-safe: replace + with -, / with _, and remove padding =
        return base64
            .replace('+', '-')
            .replace('/', '_')
            .replace("=", "")
    }

    // Helper functions using js() to access native JavaScript APIs

    private fun isNodeJs(): Boolean =
        js(
            """
        typeof process !== 'undefined' &&
        process.versions != null &&
        process.versions.node != null
    """,
        ) as Boolean

    @Suppress("UNUSED_PARAMETER")
    private fun sha256NodeJs(input: dynamic): dynamic {
        // Use eval to prevent webpack from trying to bundle 'crypto' module
        return js(
            """
            (function() {
                var crypto = eval("require")('crypto');
                return crypto.createHash('sha256').update(input).digest();
            })()
        """,
        )
    }

    @Suppress("UNUSED_PARAMETER")
    private fun sha256Browser(input: dynamic): dynamic {
        // Pure JavaScript SHA-256 implementation for browsers (synchronous)
        return js(
            """
            (function(data) {
                // Convert Uint8Array to regular array for processing
                var msg = Array.from(data);

                // SHA-256 constants
                var K = [
                    0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
                    0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
                    0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
                    0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
                    0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
                    0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
                    0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
                    0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
                ];

                // Initial hash values
                var H = [
                    0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a,
                    0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19
                ];

                // Pre-processing
                var l = msg.length * 8;
                msg.push(0x80);
                while ((msg.length % 64) !== 56) {
                    msg.push(0x00);
                }

                // Append length
                for (var i = 7; i >= 0; i--) {
                    msg.push((l >>> (i * 8)) & 0xff);
                }

                // Process message in 512-bit chunks
                for (var chunk = 0; chunk < msg.length; chunk += 64) {
                    var w = [];
                    for (var i = 0; i < 16; i++) {
                        w[i] = (msg[chunk + i * 4] << 24) | (msg[chunk + i * 4 + 1] << 16) |
                               (msg[chunk + i * 4 + 2] << 8) | msg[chunk + i * 4 + 3];
                    }

                    for (var i = 16; i < 64; i++) {
                        var s0 = ((w[i-15] >>> 7) | (w[i-15] << 25)) ^ ((w[i-15] >>> 18) | (w[i-15] << 14)) ^ (w[i-15] >>> 3);
                        var s1 = ((w[i-2] >>> 17) | (w[i-2] << 15)) ^ ((w[i-2] >>> 19) | (w[i-2] << 13)) ^ (w[i-2] >>> 10);
                        w[i] = (w[i-16] + s0 + w[i-7] + s1) | 0;
                    }

                    var a = H[0], b = H[1], c = H[2], d = H[3];
                    var e = H[4], f = H[5], g = H[6], h = H[7];

                    for (var i = 0; i < 64; i++) {
                        var S1 = ((e >>> 6) | (e << 26)) ^ ((e >>> 11) | (e << 21)) ^ ((e >>> 25) | (e << 7));
                        var ch = (e & f) ^ (~e & g);
                        var temp1 = (h + S1 + ch + K[i] + w[i]) | 0;
                        var S0 = ((a >>> 2) | (a << 30)) ^ ((a >>> 13) | (a << 19)) ^ ((a >>> 22) | (a << 10));
                        var maj = (a & b) ^ (a & c) ^ (b & c);
                        var temp2 = (S0 + maj) | 0;

                        h = g; g = f; f = e; e = (d + temp1) | 0;
                        d = c; c = b; b = a; a = (temp1 + temp2) | 0;
                    }

                    H[0] = (H[0] + a) | 0; H[1] = (H[1] + b) | 0;
                    H[2] = (H[2] + c) | 0; H[3] = (H[3] + d) | 0;
                    H[4] = (H[4] + e) | 0; H[5] = (H[5] + f) | 0;
                    H[6] = (H[6] + g) | 0; H[7] = (H[7] + h) | 0;
                }

                // Produce final hash
                var result = new Uint8Array(32);
                for (var i = 0; i < 8; i++) {
                    result[i * 4] = (H[i] >>> 24) & 0xff;
                    result[i * 4 + 1] = (H[i] >>> 16) & 0xff;
                    result[i * 4 + 2] = (H[i] >>> 8) & 0xff;
                    result[i * 4 + 3] = H[i] & 0xff;
                }
                return result;
            })(input)
        """,
        )
    }

    @Suppress("UNUSED_PARAMETER")
    private fun base64EncodeNodeJs(input: ByteArray): String {
        // Use eval to prevent webpack from trying to bundle 'Buffer'
        return js("eval('Buffer').from(input).toString('base64')") as String
    }

    private fun base64EncodeBrowser(input: ByteArray): String {
        // Convert ByteArray to string of characters, then use btoa
        @Suppress("UNUSED_VARIABLE", "unused")
        val binaryString = input.joinToString("") { (it.toInt() and 0xFF).toChar().toString() }
        return js("btoa(binaryString)") as String
    }

    // Conversion helpers
    private fun ByteArray.toUint8Array(): dynamic {
        val len = this.size
        val result = js("new Uint8Array(len)")
        for (i in indices) {
            result[i] = this[i]
        }
        return result
    }

    private fun jsArrayToByteArray(jsArray: dynamic): ByteArray {
        val length = jsArray.length as Int
        return ByteArray(length) { i ->
            jsArray[i] as Byte
        }
    }
}
