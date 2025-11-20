package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PuzzleRaceResults(
    val id: String,
    val owner: String,
    val players: List<Player>,
    val puzzles: List<Puzzle>,
    val finishesAt: Long,
    val startsAt: Long,
) {
    @Serializable
    data class Player(
        val name: String,
        val score: Long,
        val id: String? = null,
        val flair: String? = null,
        @Deprecated("patron is deprecated")
        val patron: Boolean? = null,
        val patronColor: PatronColor? = null,
    )

    @Serializable
    data class Puzzle(
        val id: String,
        val fen: String,
        val line: String,
        val rating: Long,
    )
}
