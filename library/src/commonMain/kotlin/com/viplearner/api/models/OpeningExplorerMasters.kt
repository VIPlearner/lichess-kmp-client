package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class OpeningExplorerMasters(
    val opening: OpeningExplorerOpening?,
    val white: Long,
    val draws: Long,
    val black: Long,
    val moves: List<Move>,
    val topGames: List<TopGame>,
) {
    @Serializable
    data class Move(
        val uci: String,
        val san: String,
        val averageRating: Long,
        val white: Long,
        val draws: Long,
        val black: Long,
        val game: OpeningExplorerMastersGame?,
        val opening: OpeningExplorerOpening?,
    )

    @Serializable
    data class TopGame(
        val uci: String,
        val id: String,
        val winner: GameColor?,
        val white: OpeningExplorerGamePlayer,
        val black: OpeningExplorerGamePlayer,
        val year: Long,
        val month: String? = null,
    )
}
