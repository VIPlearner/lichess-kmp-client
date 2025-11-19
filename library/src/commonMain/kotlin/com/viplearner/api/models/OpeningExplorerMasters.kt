package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class OpeningExplorerMasters(
    val opening: OpeningExplorerOpening?,
    val white: Int,
    val draws: Int,
    val black: Int,
    val moves: List<Move>,
    val topGames: List<TopGame>
) {
    @Serializable
    data class Move(
        val uci: String,
        val san: String,
        val averageRating: Int,
        val white: Int,
        val draws: Int,
        val black: Int,
        val game: OpeningExplorerMastersGame?,
        val opening: OpeningExplorerOpening?
    )

    @Serializable
    data class TopGame(
        val uci: String,
        val id: String,
        val winner: GameColor?,
        val white: OpeningExplorerGamePlayer,
        val black: OpeningExplorerGamePlayer,
        val year: Int,
        val month: String? = null
    )
}
