package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PuzzleStormDashboardHigh(
    val allTime: Long,
    val day: Long,
    val month: Long,
    val week: Long,
)

@Serializable
data class PuzzleStormDashboard(
    val days: List<Day>,
    val high: PuzzleStormDashboardHigh,
) {
    @Serializable
    data class Day(
        @SerialName("_id")
        val id: String,
        val combo: Long,
        val errors: Long,
        val highest: Long,
        val moves: Long,
        val runs: Long,
        val score: Long,
        val time: Long,
    )
}
