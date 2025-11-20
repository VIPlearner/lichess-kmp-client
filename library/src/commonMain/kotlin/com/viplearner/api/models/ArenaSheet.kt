package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ArenaSheet(
    val scores: String,
    val fire: Boolean? = null,
)
