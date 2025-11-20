package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PuzzleAndGameGame(
    val clock: String,
    val id: String,
    val perf: Perf,
    val pgn: String,
    val players: List<Player>,
    val rated: Boolean,
) {
    @Serializable
    data class Perf(
        val key: String,
        val name: String,
    )

    @Serializable
    data class Player(
        val color: String,
        val flair: Flair? = null,
        val id: String,
        val name: String,
        @Deprecated("patron is deprecated")
        val patron: Boolean? = null,
        val patronColor: PatronColor? = null,
        val rating: Long,
        val title: Title? = null,
    )
}

@Serializable
data class PuzzleAndGamePuzzle(
    val id: String,
    val initialPly: Long,
    val plays: Long,
    val rating: Long,
    val solution: List<String>,
    val themes: List<String>,
)

@Serializable
data class PuzzleAndGame(
    val game: PuzzleAndGameGame,
    val puzzle: PuzzleAndGamePuzzle,
)
