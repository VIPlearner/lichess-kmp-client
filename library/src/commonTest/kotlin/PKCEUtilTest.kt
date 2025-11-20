package com.viplearner.api.client

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PKCEUtilTest {
    @Test
    fun testGenerateCodeVerifier() {
        val verifier = PKCEUtil.generateCodeVerifier()

        // Check length is default 64
        assertEquals(64, verifier.length)

        // Check contains only valid characters
        val validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-._~"
        assertTrue(verifier.all { it in validChars })
    }

    @Test
    fun testGenerateCodeVerifierCustomLength() {
        val verifier = PKCEUtil.generateCodeVerifier(128)
        assertEquals(128, verifier.length)
    }

    @Test
    fun testGenerateCodeChallenge() {
        val verifier = "test_verifier_12345678901234567890123456789012"
        val challenge = PKCEUtil.generateCodeChallenge(verifier)

        // Challenge should be base64url encoded (no padding)
        assertNotNull(challenge)
        assertTrue(challenge.isNotEmpty())
        assertTrue(!challenge.contains("="))
        assertTrue(!challenge.contains("+"))
        assertTrue(!challenge.contains("/"))
    }

    @Test
    fun testCodeChallengeIsDeterministic() {
        val verifier = "my_test_verifier"
        val challenge1 = PKCEUtil.generateCodeChallenge(verifier)
        val challenge2 = PKCEUtil.generateCodeChallenge(verifier)

        // Same input should produce same output
        assertEquals(challenge1, challenge2)
    }

    @Test
    fun testCodeChallengeIsUnique() {
        val verifier1 = "verifier_1"
        val verifier2 = "verifier_2"
        val challenge1 = PKCEUtil.generateCodeChallenge(verifier1)
        val challenge2 = PKCEUtil.generateCodeChallenge(verifier2)

        // Different inputs should produce different outputs
        assertTrue(challenge1 != challenge2)
    }
}
