package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GameJsonClock(
    val initial: Int,
    val increment: Int,
    val totalTime: Int
)

@Serializable
data class GameJsonDivision(
    val middle: Int? = null,
    val end: Int? = null
)

@Serializable
data class GameJson(
    val id: String,
    val rated: Boolean,
    val variant: VariantKey,
    val speed: Speed,
    val perf: String,
    val createdAt: Long,
    val lastMoveAt: Long,
    val status: GameStatusName,
    val source: String? = null,
    val players: GamePlayers,
    val initialFen: String? = null,
    val winner: GameColor? = null,
    val opening: GameOpening? = null,
    val moves: String? = null,
    val pgn: String? = null,
    val daysPerTurn: Int? = null,
    val analysis: List<GameMoveAnalysis>? = null,
    val tournament: String? = null,
    val swiss: String? = null,
    val clock: GameJsonClock? = null,
    val clocks: List<Int>? = null,
    val division: GameJsonDivision? = null
)
