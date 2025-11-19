package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class OpeningExplorerPlayer(
    val opening: OpeningExplorerOpening?,
    val queuePosition: Int,
    val white: Int,
    val draws: Int,
    val black: Int,
    val moves: List<Move>,
    val recentGames: List<RecentGame>
) {
    @Serializable
    data class Move(
        val uci: String,
        val san: String,
        val averageOpponentRating: Int,
        val performance: Int,
        val white: Int,
        val draws: Int,
        val black: Int,
        val game: OpeningExplorerPlayerGame?,
        val opening: OpeningExplorerOpening?
    )

    @Serializable
    data class RecentGame(
        val uci: String,
        val id: String,
        val winner: GameColor?,
        val speed: Speed,
        val mode: OpeningExplorerPlayerGameMode,
        val white: OpeningExplorerGamePlayer,
        val black: OpeningExplorerGamePlayer,
        val year: Int,
        val month: String
    )
}
