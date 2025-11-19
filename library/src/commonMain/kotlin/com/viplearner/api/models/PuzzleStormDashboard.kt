package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PuzzleStormDashboardHigh(
    val allTime: Int,
    val day: Int,
    val month: Int,
    val week: Int
)

@Serializable
data class PuzzleStormDashboard(
    val days: List<Day>,
    val high: PuzzleStormDashboardHigh
) {
    @Serializable
    data class Day(
        @SerialName("_id")
        val id: String,
        val combo: Int,
        val errors: Int,
        val highest: Int,
        val moves: Int,
        val runs: Int,
        val score: Int,
        val time: Int
    )
}
