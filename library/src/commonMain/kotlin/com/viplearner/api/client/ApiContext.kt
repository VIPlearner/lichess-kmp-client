package com.viplearner.api.client

import com.viplearner.api.client.auth.AuthProvider
import com.viplearner.api.client.clientid.ClientIdProvider
import io.ktor.client.HttpClient

data class ApiContext(
    val baseUrl: String,
    val httpClient: HttpClient,
    val authProvider: AuthProvider,
    val clientIdProvider: ClientIdProvider? = null,
)
