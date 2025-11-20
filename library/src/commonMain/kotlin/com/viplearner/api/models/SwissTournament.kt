package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SwissTournamentClock(
    val limit: Double,
    val increment: Double,
)

@Serializable
data class SwissTournamentStats(
    val games: Double,
    val whiteWins: Double,
    val blackWins: Double,
    val draws: Double,
    val byes: Double,
    val absences: Double,
    val averageRating: Double,
)

@Serializable
data class SwissTournamentNextround(
    val at: String? = null,
    @SerialName("in")
    val `in`: Long? = null,
)

@Serializable
data class SwissTournament(
    val id: String,
    val createdBy: String,
    val startsAt: String,
    val name: String,
    val clock: SwissTournamentClock,
    val variant: String,
    val round: Double,
    val nbRounds: Double,
    val nbPlayers: Double,
    val nbOngoing: Double,
    val status: SwissStatus,
    val stats: SwissTournamentStats? = null,
    val rated: Boolean,
    val verdicts: Verdicts,
    val nextRound: SwissTournamentNextround? = null,
)
