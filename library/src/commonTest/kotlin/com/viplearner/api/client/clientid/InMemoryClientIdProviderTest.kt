package com.viplearner.api.client.clientid

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class InMemoryClientIdProviderTest {
    @Test
    fun testGeneratesUUID() {
        val provider = InMemoryClientIdProvider()
        val clientId = provider.getClientId()

        assertNotNull(clientId)
        // UUID format: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx (36 chars)
        assertEquals(36, clientId.length)
    }

    @Test
    fun testReturnsSameIdOnMultipleCalls() {
        val provider = InMemoryClientIdProvider()

        val id1 = provider.getClientId()
        val id2 = provider.getClientId()
        val id3 = provider.getClientId()

        assertEquals(id1, id2)
        assertEquals(id2, id3)
    }

    @Test
    fun testDifferentInstancesGenerateDifferentIds() {
        val provider1 = InMemoryClientIdProvider()
        val provider2 = InMemoryClientIdProvider()

        val id1 = provider1.getClientId()
        val id2 = provider2.getClientId()

        assertNotEquals(id1, id2)
    }

    @Test
    fun testLazyInitialization() {
        val provider = InMemoryClientIdProvider()
        // No ID generated yet until first call

        val id = provider.getClientId()
        assertNotNull(id)

        // Subsequent calls return same ID
        assertEquals(id, provider.getClientId())
    }
}
