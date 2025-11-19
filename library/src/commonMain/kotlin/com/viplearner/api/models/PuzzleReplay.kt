package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PuzzleReplayReplay(
    val days: Int,
    val theme: String,
    val nb: Int,
    val remaining: List<String>
)

@Serializable
data class PuzzleReplayAngle(
    val key: String,
    val name: String,
    val desc: String
)

@Serializable
data class PuzzleReplay(
    val replay: PuzzleReplayReplay,
    val angle: PuzzleReplayAngle
)
