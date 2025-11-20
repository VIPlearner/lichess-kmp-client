package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ExternalEngineRegistration(
    val name: String,
    val maxThreads: Long,
    val maxHash: Long,
    val variants: List<UciVariant>? = null,
    val providerSecret: String,
    val providerData: String? = null,
)
