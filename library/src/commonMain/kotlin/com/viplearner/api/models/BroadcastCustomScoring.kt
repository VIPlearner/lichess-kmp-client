package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastCustomScoring(
    val white: BroadcastCustomPointsPerColor,
    val black: BroadcastCustomPointsPerColor,
)
