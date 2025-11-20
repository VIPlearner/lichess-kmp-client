package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastGroup(
    val id: String,
    val slug: String,
    val name: String,
    val tours: List<BroadcastGroupTour>,
)
