package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class BroadcastGameEntryPoints {
    @SerialName("1")
    VALUE_1,

    @SerialName("1/2")
    VALUE_1_2,

    @SerialName("0")
    VALUE_0,
}

@Serializable
data class BroadcastGameEntry(
    val round: String,
    val id: String,
    val opponent: BroadcastPlayerWithFed,
    val color: GameColor,
    val points: BroadcastGameEntryPoints? = null,
    val customPoints: BroadcastCustomPoints? = null,
    val ratingDiff: Long? = null,
)
