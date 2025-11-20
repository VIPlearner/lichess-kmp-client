package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastPlayerWithFed(
    val name: String,
    val title: Title? = null,
    val rating: Long? = null,
    val fideId: Long? = null,
    val team: String? = null,
    val fed: String? = null,
)
