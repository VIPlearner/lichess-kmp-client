package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TimelineEntryTeamJoinType {
    @SerialName("team-join")
    TEAM_JOIN,
}

@Serializable
data class TimelineEntryTeamJoinData(
    val userId: String,
    val teamId: String,
)

@Serializable
data class TimelineEntryTeamJoin(
    val type: TimelineEntryTeamJoinType,
    val date: Double,
    val data: TimelineEntryTeamJoinData,
)
