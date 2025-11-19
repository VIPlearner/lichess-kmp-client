package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PuzzleBatchSolveResponse(
    val puzzles: List<PuzzleAndGame>? = null,
    val rounds: List<String>? = null
)
