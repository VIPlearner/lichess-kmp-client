package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ExternalEngine(
    val id: String,
    val name: String,
    val clientSecret: String,
    val userId: String,
    val maxThreads: Long,
    val maxHash: Long,
    val variants: List<UciVariant>,
    val providerData: String? = null,
)
