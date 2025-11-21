package com.viplearner.api.client

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PKCEMultiplatformTest {
    @Test
    fun testSha256HashesCorrectly() {
        val input = "test_string".encodeToByteArray()
        val hash = PKCEMultiplatform.sha256(input)

        assertNotNull(hash)
        assertEquals(32, hash.size, "SHA-256 hash should be 32 bytes")
    }

    @Test
    fun testBase64UrlEncodeFormatsCorrectly() {
        val input = byteArrayOf(1, 2, 3, 4, 5)
        val encoded = PKCEMultiplatform.base64UrlEncode(input)

        assertNotNull(encoded)
        assertTrue(encoded.isNotEmpty())
        // URL-safe base64 should not contain + or /
        assertTrue(!encoded.contains('+'))
        assertTrue(!encoded.contains('/'))
        // Should not have padding
        assertTrue(!encoded.endsWith('='))
    }

    @Test
    fun testSha256IsConsistent() {
        val input = "consistent_test".encodeToByteArray()
        val hash1 = PKCEMultiplatform.sha256(input)
        val hash2 = PKCEMultiplatform.sha256(input)

        assertTrue(hash1.contentEquals(hash2), "Same input should produce same hash")
    }

    @Test
    fun testBase64UrlEncodeIsConsistent() {
        val input = byteArrayOf(10, 20, 30, 40, 50)
        val encoded1 = PKCEMultiplatform.base64UrlEncode(input)
        val encoded2 = PKCEMultiplatform.base64UrlEncode(input)

        assertEquals(encoded1, encoded2, "Same input should produce same encoding")
    }

    @Test
    fun testEmptyInputHandling() {
        val emptyInput = byteArrayOf()
        val hash = PKCEMultiplatform.sha256(emptyInput)
        val encoded = PKCEMultiplatform.base64UrlEncode(emptyInput)

        assertNotNull(hash)
        assertNotNull(encoded)
        assertEquals(32, hash.size, "SHA-256 of empty input should still be 32 bytes")
    }
}
