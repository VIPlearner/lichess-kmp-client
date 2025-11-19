package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class BroadcastPlayerWithFed(
    val name: String,
    val title: Title? = null,
    val rating: Int? = null,
    val fideId: Int? = null,
    val team: String? = null,
    val fed: String? = null
)
