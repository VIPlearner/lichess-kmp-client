package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastCustomPointsPerColor(
    val win: BroadcastCustomPoints,
    val draw: BroadcastCustomPoints,
)
