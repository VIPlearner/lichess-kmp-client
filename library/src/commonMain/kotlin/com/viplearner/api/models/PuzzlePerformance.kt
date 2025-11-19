package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PuzzlePerformance(
    val firstWins: Int,
    val nb: Int,
    val performance: Int,
    val puzzleRatingAvg: Int,
    val replayWins: Int
)
