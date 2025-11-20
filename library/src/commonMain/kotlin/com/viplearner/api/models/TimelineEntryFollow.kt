package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TimelineEntryFollowType {
    @SerialName("follow")
    FOLLOW,
}

@Serializable
data class TimelineEntryFollowData(
    val u1: String,
    val u2: String,
)

@Serializable
data class TimelineEntryFollow(
    val type: TimelineEntryFollowType,
    val date: Double,
    val data: TimelineEntryFollowData,
)
