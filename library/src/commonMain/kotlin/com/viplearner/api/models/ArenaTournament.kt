package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ArenaTournamentMinratedgames(
    val nb: Long? = null,
)

@Serializable
data class ArenaTournamentSchedule(
    val freq: String? = null,
    val speed: String? = null,
)

@Serializable
data class ArenaTournamentTeambattle(
    val teams: List<String>? = null,
    val nbLeaders: Long? = null,
)

@Serializable
data class ArenaTournament(
    val id: String,
    val createdBy: String,
    val system: String,
    val minutes: Long,
    val clock: Clock,
    val rated: Boolean,
    val fullName: String,
    val nbPlayers: Long,
    val variant: Variant,
    val startsAt: Long,
    val finishesAt: Long,
    val status: ArenaStatus,
    val perf: ArenaPerf,
    val secondsToStart: Long? = null,
    val hasMaxRating: Boolean? = null,
    val maxRating: ArenaRatingObj? = null,
    val minRating: ArenaRatingObj? = null,
    val minRatedGames: ArenaTournamentMinratedgames? = null,
    val botsAllowed: Boolean? = null,
    val minAccountAgeInDays: Long? = null,
    val onlyTitled: Boolean? = null,
    val teamMember: String? = null,
    val private: Boolean? = null,
    val position: ArenaPosition? = null,
    val schedule: ArenaTournamentSchedule? = null,
    val teamBattle: ArenaTournamentTeambattle? = null,
    val winner: LightUser? = null,
)
