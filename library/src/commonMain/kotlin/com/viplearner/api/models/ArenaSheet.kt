package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ArenaSheet(
    val scores: String,
    val fire: Boolean? = null
)
