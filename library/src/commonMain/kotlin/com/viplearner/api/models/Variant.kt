package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Variant(
    val key: VariantKey,
    val name: String,
    val short: String? = null
)
