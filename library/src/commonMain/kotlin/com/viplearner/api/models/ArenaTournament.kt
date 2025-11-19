package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ArenaTournamentMinratedgames(
    val nb: Int? = null
)

@Serializable
data class ArenaTournamentSchedule(
    val freq: String? = null,
    val speed: String? = null
)

@Serializable
data class ArenaTournamentTeambattle(
    val teams: List<String>? = null,
    val nbLeaders: Int? = null
)

@Serializable
data class ArenaTournament(
    val id: String,
    val createdBy: String,
    val system: String,
    val minutes: Int,
    val clock: Clock,
    val rated: Boolean,
    val fullName: String,
    val nbPlayers: Int,
    val variant: Variant,
    val startsAt: Long,
    val finishesAt: Long,
    val status: ArenaStatus,
    val perf: ArenaPerf,
    val secondsToStart: Int? = null,
    val hasMaxRating: Boolean? = null,
    val maxRating: ArenaRatingObj? = null,
    val minRating: ArenaRatingObj? = null,
    val minRatedGames: ArenaTournamentMinratedgames? = null,
    val botsAllowed: Boolean? = null,
    val minAccountAgeInDays: Int? = null,
    val onlyTitled: Boolean? = null,
    val teamMember: String? = null,
    val private: Boolean? = null,
    val position: ArenaPosition? = null,
    val schedule: ArenaTournamentSchedule? = null,
    val teamBattle: ArenaTournamentTeambattle? = null,
    val winner: LightUser? = null
)
