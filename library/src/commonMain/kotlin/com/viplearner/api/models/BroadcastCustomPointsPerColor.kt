package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class BroadcastCustomPointsPerColor(
    val win: BroadcastCustomPoints,
    val draw: BroadcastCustomPoints
)
