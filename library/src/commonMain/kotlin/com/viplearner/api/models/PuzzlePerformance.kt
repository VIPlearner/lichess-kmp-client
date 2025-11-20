package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PuzzlePerformance(
    val firstWins: Long,
    val nb: Long,
    val performance: Long,
    val puzzleRatingAvg: Long,
    val replayWins: Long,
)
