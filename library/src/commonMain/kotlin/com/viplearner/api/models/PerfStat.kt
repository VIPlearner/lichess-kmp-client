package com.viplearner.api.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PerfStatUser(
    val name: String
)

@Serializable
data class PerfStatPerf(
    val glicko: Glicko? = null,
    val nb: Int? = null,
    val progress: Int? = null
) {
    @Serializable
    data class Glicko(
        val rating: Double,
        val deviation: Double,
        val provisional: Boolean
    )
}

@Serializable
data class PerfStatStat(
    val highest: IntAtGameId? = null,
    val lowest: IntAtGameId? = null,
    val bestWins: ResultList,
    val worstLosses: ResultList,
    val count: Count,
    val resultStreak: ResultStreak,
    val playStreak: PlayStreak
) {
    //highest, lowest
    @Serializable
    data class IntAtGameId(
        val int: Int,
        val at: LocalDateTime,
        val gameId: String
    )

    @Serializable
    data class ResultEntry(
        val opRating: Int,
        val opId: LightUser,
        val at: String,
        val gameId: String
    )

    @Serializable
    data class ResultList(
        val results: List<ResultEntry>
    )

    @Serializable
    data class Count(
        val all: Int,
        val rated: Int,
        val win: Int,
        val loss: Int,
        val draw: Int,
        val tour: Int,
        val berserk: Int,
        val opAvg: Double,
        val seconds: Int,
        val disconnects: Int
    )

    @Serializable
    data class ResultStreakEntry(
        val v: Int,
        val from: AtGameId,
        val to: AtGameId
    ) {
        @Serializable
        data class AtGameId(
            val at: LocalDateTime,
            val gameId: String
        )
    }

    @Serializable
    data class ResultStreak(
        val cur: ResultStreakEntry,
        val max: ResultStreakEntry
    )

    @Serializable
    data class PlayStreakEntry(
        val v: Int,
        val from: AtGameId? = null,
        val to: AtGameId? = null
    ) {
        @Serializable
        data class AtGameId(
            val at: LocalDateTime,
            val gameId: String
        )
    }

    @Serializable
    data class PlayStreak(
        val nb: PlayStreakEntry,
        val time: PlayStreakEntry,
        val lastDate: LocalDateTime
    )
}

@Serializable
data class PerfStat(
    val user: PerfStatUser,
    val perf: PerfStatPerf,
    val rank: Int?,
    val percentile: Double,
    val stat: PerfStatStat
)
