package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class OpeningExplorerLichess(
    val opening: OpeningExplorerOpening?,
    val white: Int,
    val draws: Int,
    val black: Int,
    val moves: List<Move>,
    val topGames: List<TopGame>,
    val recentGames: List<RecentGame>? = null,
    val history: List<HistoryEntry>? = null
) {
    @Serializable
    data class Move(
        val uci: String,
        val san: String,
        val averageRating: Int,
        val white: Int,
        val draws: Int,
        val black: Int,
        val game: OpeningExplorerLichessGame?,
        val opening: OpeningExplorerOpening?
    )

    @Serializable
    data class TopGame(
        val uci: String,
        val id: String,
        val winner: GameColor?,
        val speed: Speed? = null,
        val white: OpeningExplorerGamePlayer,
        val black: OpeningExplorerGamePlayer,
        val year: Double,
        val month: String?
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
        val year: Double,
        val month: String
    )

    @Serializable
    data class HistoryEntry(
        val month: String,
        val white: Int,
        val draws: Int,
        val black: Int
    )
}
