package com.viplearner.api.client.auth

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class NoAuthProviderTest {
    @Test
    fun testReturnsNull() =
        runTest {
            val provider = NoAuthProvider()
            assertNull(provider.authHeader())
        }

    @Test
    fun testCloseDoesNotThrow() {
        val provider = NoAuthProvider()
        provider.close() // Should not throw
    }
}

class StaticTokenAuthProviderTest {
    @Test
    fun testReturnsBearerToken() =
        runTest {
            val token = "test_token_12345"
            val provider = StaticTokenAuthProvider(token)

            val header = provider.authHeader()
            assertEquals("Bearer $token", header)
        }

    @Test
    fun testConsistentToken() =
        runTest {
            val token = "my_access_token"
            val provider = StaticTokenAuthProvider(token)

            val header1 = provider.authHeader()
            val header2 = provider.authHeader()

            assertEquals(header1, header2)
        }
}
