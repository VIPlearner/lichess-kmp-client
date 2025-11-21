package com.viplearner.api.client.auth

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OAuthAuthProviderTest {
    private class MockOAuthManager(
        private val mockToken: TokenInfo,
        private var callCount: Int = 0,
    ) : OAuthManager {
        fun getCallCount() = callCount

        override suspend fun obtainToken(clientId: String): TokenInfo {
            callCount++
            return mockToken
        }
    }

    @Test
    fun testObtainsTokenOnFirstCall() =
        runTest {
            val mockToken = TokenInfo("access_token_123", null)
            val mockManager = MockOAuthManager(mockToken)
            val provider = OAuthAuthProvider("test_client", mockManager)

            val header = provider.authHeader()

            assertEquals("Bearer access_token_123", header)
            assertEquals(1, mockManager.getCallCount())
        }

    @Test
    fun testCachesToken() =
        runTest {
            val mockToken = TokenInfo("cached_token", null)
            val mockManager = MockOAuthManager(mockToken)
            val provider = OAuthAuthProvider("test_client", mockManager)

            val header1 = provider.authHeader()
            val header2 = provider.authHeader()
            val header3 = provider.authHeader()

            assertEquals(header1, header2)
            assertEquals(header2, header3)
            assertEquals(1, mockManager.getCallCount(), "Token should be cached, manager called only once")
        }

    @OptIn(kotlin.time.ExperimentalTime::class)
    @Test
    fun testRefreshesExpiredToken() =
        runTest {
            val expiredTime = kotlin.time.Clock.System.now().toEpochMilliseconds() / 1000 - 100
            val expiredToken = TokenInfo("old_token", expiredTime)
            val newToken = TokenInfo("new_token", null)

            var callCount = 0
            val mockManager =
                object : OAuthManager {
                    override suspend fun obtainToken(clientId: String): TokenInfo {
                        callCount++
                        return if (callCount == 1) expiredToken else newToken
                    }
                }

            val provider = OAuthAuthProvider("test_client", mockManager)

            val header1 = provider.authHeader()
            assertEquals("Bearer old_token", header1)

            // Second call should detect expired token and refresh
            val header2 = provider.authHeader()
            assertEquals("Bearer new_token", header2)
            assertEquals(2, callCount)
        }

    @Test
    fun testThreadSafety() =
        runTest {
            val mockToken = TokenInfo("concurrent_token", null)
            val mockManager = MockOAuthManager(mockToken)
            val provider = OAuthAuthProvider("test_client", mockManager)

            // Simulate concurrent access
            val results =
                List(10) {
                    provider.authHeader()
                }

            assertTrue(results.all { it == "Bearer concurrent_token" })
            assertEquals(1, mockManager.getCallCount(), "Should only call manager once despite concurrent access")
        }
}
