package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PuzzleBatchSelect(
    val puzzles: List<PuzzleAndGame>? = null,
)
