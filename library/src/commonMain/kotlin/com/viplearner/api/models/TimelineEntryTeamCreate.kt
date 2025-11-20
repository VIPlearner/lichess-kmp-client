package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TimelineEntryTeamCreateType {
    @SerialName("team-create")
    TEAM_CREATE,
}

@Serializable
data class TimelineEntryTeamCreateData(
    val userId: String,
    val teamId: String,
)

@Serializable
data class TimelineEntryTeamCreate(
    val type: TimelineEntryTeamCreateType,
    val date: Double,
    val data: TimelineEntryTeamCreateData,
)
