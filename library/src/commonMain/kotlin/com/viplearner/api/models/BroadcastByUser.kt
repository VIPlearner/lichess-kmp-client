package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastByUser(
    val tour: BroadcastTour,
)
