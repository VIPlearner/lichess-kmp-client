package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class GameEventInfo(
    val fullId: String,
    val gameId: String,
    val fen: String? = null,
    val color: GameColor? = null,
    val lastMove: String? = null,
    val source: GameSource? = null,
    val status: GameStatus? = null,
    val variant: Variant? = null,
    val speed: Speed? = null,
    val perf: String? = null,
    val rated: Boolean? = null,
    val hasMoved: Boolean? = null,
    val opponent: GameEventOpponent? = null,
    val isMyTurn: Boolean? = null,
    val secondsLeft: Long? = null,
    val winner: GameColor? = null,
    val ratingDiff: Long? = null,
    val compat: GameCompat? = null,
    val id: String? = null,
    val tournamentId: String? = null,
)
