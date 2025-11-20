package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PuzzleReplayReplay(
    val days: Long,
    val theme: String,
    val nb: Long,
    val remaining: List<String>,
)

@Serializable
data class PuzzleReplayAngle(
    val key: String,
    val name: String,
    val desc: String,
)

@Serializable
data class PuzzleReplay(
    val replay: PuzzleReplayReplay,
    val angle: PuzzleReplayAngle,
)
