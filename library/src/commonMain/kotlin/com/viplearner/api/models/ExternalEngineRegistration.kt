package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ExternalEngineRegistration(
    val name: String,
    val maxThreads: Int,
    val maxHash: Int,
    val variants: List<UciVariant>? = null,
    val providerSecret: String,
    val providerData: String? = null
)
