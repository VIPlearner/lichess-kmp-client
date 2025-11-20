package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class OpeningExplorerPlayer(
    val opening: OpeningExplorerOpening?,
    val queuePosition: Long,
    val white: Long,
    val draws: Long,
    val black: Long,
    val moves: List<Move>,
    val recentGames: List<RecentGame>,
) {
    @Serializable
    data class Move(
        val uci: String,
        val san: String,
        val averageOpponentRating: Long,
        val performance: Long,
        val white: Long,
        val draws: Long,
        val black: Long,
        val game: OpeningExplorerPlayerGame?,
        val opening: OpeningExplorerOpening?,
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
        val year: Long,
        val month: String,
    )
}
