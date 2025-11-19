package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PuzzleBatchSelect(
    val puzzles: List<PuzzleAndGame>? = null
)
