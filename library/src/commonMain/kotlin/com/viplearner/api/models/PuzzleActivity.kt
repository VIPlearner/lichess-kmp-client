package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PuzzleActivityPuzzle(
    val fen: String,
    val id: String,
    val lastMove: String,
    val plays: Long,
    val rating: Long,
    val solution: List<String>,
    val themes: List<String>,
)

@Serializable
data class PuzzleActivity(
    val date: Long,
    val puzzle: PuzzleActivityPuzzle,
    val win: Boolean,
)
