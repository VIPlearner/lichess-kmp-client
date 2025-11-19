package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ExternalEngine(
    val id: String,
    val name: String,
    val clientSecret: String,
    val userId: String,
    val maxThreads: Int,
    val maxHash: Int,
    val variants: List<UciVariant>,
    val providerData: String? = null
)
