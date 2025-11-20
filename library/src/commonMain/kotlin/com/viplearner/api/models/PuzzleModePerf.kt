package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PuzzleModePerf(
    val runs: Long,
    val score: Long,
)
