package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GameFullEventClock(
    val initial: Long? = null,
    val increment: Long? = null
)

@Serializable
data class GameFullEventPerf(
    val name: String? = null
)

@Serializable
data class GameFullEvent(
    val type: String,
    val id: String,
    val variant: Variant,
    val clock: GameFullEventClock? = null,
    val speed: Speed,
    val perf: GameFullEventPerf,
    val rated: Boolean,
    val createdAt: Long,
    val white: GameEventPlayer,
    val black: GameEventPlayer,
    val initialFen: String,
    val state: GameStateEvent,
    val daysPerTurn: Int? = null,
    val tournamentId: String? = null
)
