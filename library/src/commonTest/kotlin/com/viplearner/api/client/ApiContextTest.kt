package com.viplearner.api.client

import com.viplearner.api.client.auth.NoAuthProvider
import com.viplearner.api.client.auth.StaticTokenAuthProvider
import com.viplearner.api.client.clientid.InMemoryClientIdProvider
import io.ktor.client.HttpClient
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ApiContextTest {
    @Test
    fun testCreateWithNoAuth() {
        val httpClient = HttpClient()
        val context =
            ApiContext(
                baseUrl = "https://lichess.org/api",
                httpClient = httpClient,
                authProvider = NoAuthProvider(),
            )

        assertEquals("https://lichess.org/api", context.baseUrl)
        assertNotNull(context.httpClient)
        assertNotNull(context.authProvider)
        assertNull(context.clientIdProvider)

        httpClient.close()
    }

    @Test
    fun testCreateWithTokenAuth() {
        val httpClient = HttpClient()
        val context =
            ApiContext(
                baseUrl = "https://lichess.org/api",
                httpClient = httpClient,
                authProvider = StaticTokenAuthProvider("test_token"),
            )

        assertNotNull(context.authProvider)

        httpClient.close()
    }

    @Test
    fun testCreateWithClientIdProvider() {
        val httpClient = HttpClient()
        val context =
            ApiContext(
                baseUrl = "https://lichess.org/api",
                httpClient = httpClient,
                authProvider = NoAuthProvider(),
                clientIdProvider = InMemoryClientIdProvider(),
            )

        assertNotNull(context.clientIdProvider)

        httpClient.close()
    }

    @Test
    fun testCreateWithAllComponents() {
        val httpClient = HttpClient()
        val context =
            ApiContext(
                baseUrl = "https://custom.lichess.org/api",
                httpClient = httpClient,
                authProvider = StaticTokenAuthProvider("token123"),
                clientIdProvider = InMemoryClientIdProvider(),
            )

        assertEquals("https://custom.lichess.org/api", context.baseUrl)
        assertNotNull(context.httpClient)
        assertNotNull(context.authProvider)
        assertNotNull(context.clientIdProvider)

        httpClient.close()
    }
}
