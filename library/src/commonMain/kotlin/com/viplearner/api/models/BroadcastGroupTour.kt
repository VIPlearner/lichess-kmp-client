package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastGroupTour(
    val id: String,
    val name: String,
    val active: Boolean,
    val live: Boolean,
)
