package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TimelineEntryTourJoinType {
    @SerialName("tour-join")
    TOUR_JOIN,
}

@Serializable
data class TimelineEntryTourJoinData(
    val userId: String,
    val tourId: String,
    val tourName: String,
)

@Serializable
data class TimelineEntryTourJoin(
    val type: TimelineEntryTourJoinType,
    val date: Double,
    val data: TimelineEntryTourJoinData,
)
