package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class TimelineEntryGameEndType {
    @SerialName("game-end")
    GAME_END
}

@Serializable
data class TimelineEntryGameEndData(
    val fullId: String,
    val opponent: String,
    val win: Boolean,
    val perf: PerfType
)

@Serializable
data class TimelineEntryGameEnd(
    val type: TimelineEntryGameEndType,
    val date: Double,
    val data: TimelineEntryGameEndData
)
