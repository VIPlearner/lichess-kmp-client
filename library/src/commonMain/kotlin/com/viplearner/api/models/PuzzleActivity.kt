package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PuzzleActivityPuzzle(
    val fen: String,
    val id: String,
    val lastMove: String,
    val plays: Int,
    val rating: Int,
    val solution: List<String>,
    val themes: List<String>
)

@Serializable
data class PuzzleActivity(
    val date: Int,
    val puzzle: PuzzleActivityPuzzle,
    val win: Boolean
)
