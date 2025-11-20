package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class GameJsonClock(
    val initial: Long,
    val increment: Long,
    val totalTime: Long,
)

@Serializable
data class GameJsonDivision(
    val middle: Long? = null,
    val end: Long? = null,
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
    val daysPerTurn: Long? = null,
    val analysis: List<GameMoveAnalysis>? = null,
    val tournament: String? = null,
    val swiss: String? = null,
    val clock: GameJsonClock? = null,
    val clocks: List<Long>? = null,
    val division: GameJsonDivision? = null,
)
