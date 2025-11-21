package com.viplearner.api.client.clientid

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class InMemoryClientIdProvider : ClientIdProvider {
    @OptIn(ExperimentalUuidApi::class)
    private val generatedClientId: String by lazy {
        Uuid.random().toString()
    }

    override fun getClientId(): String = generatedClientId
}
