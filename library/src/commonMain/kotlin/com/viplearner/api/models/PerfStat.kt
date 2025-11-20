package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PerfStatUser(
    val name: String,
)

@Serializable
data class PerfStatPerf(
    val glicko: Glicko? = null,
    val nb: Long? = null,
    val progress: Long? = null,
) {
    @Serializable
    data class Glicko(
        val rating: Double? = null,
        val deviation: Double? = null,
        val provisional: Boolean? = null,
    )
}

@Serializable
data class PerfStatStat(
    val highest: LongAtGameId? = null,
    val lowest: LongAtGameId? = null,
    val bestWins: ResultList,
    val worstLosses: ResultList,
    val count: Count,
    val resultStreak: ResultStreak,
    val playStreak: PlayStreak,
) {
    // highest, lowest
    @Serializable
    data class LongAtGameId(
        val int: Long,
        val at: String,
        val gameId: String,
    )

    @Serializable
    data class ResultEntry(
        val opRating: Long,
        val opId: LightUser,
        val at: String,
        val gameId: String,
    )

    @Serializable
    data class ResultList(
        val results: List<ResultEntry>,
    )

    @Serializable
    data class Count(
        val all: Long,
        val rated: Long,
        val win: Long,
        val loss: Long,
        val draw: Long,
        val tour: Long,
        val berserk: Long,
        val opAvg: Double,
        val seconds: Long,
        val disconnects: Long,
    )

    @Serializable
    data class ResultStreakEntry(
        val v: Long? = null,
        val from: AtGameId? = null,
        val to: AtGameId? = null,
    ) {
        @Serializable
        data class AtGameId(
            val at: String,
            val gameId: String,
        )
    }

    @Serializable
    data class ResultStreak(
        val win: Streak,
        val loss: Streak,
    ) {
        @Serializable
        data class Streak(
            val cur: ResultStreakEntry,
            val max: ResultStreakEntry,
        )
    }

    @Serializable
    data class PlayStreakEntry(
        val v: Long? = null,
        val from: AtGameId? = null,
        val to: AtGameId? = null,
    ) {
        @Serializable
        data class AtGameId(
            val at: String,
            val gameId: String,
        )
    }

    @Serializable
    data class PlayStreak(
        val nb: PlayStreakEntry,
        val time: PlayStreakEntry,
        val lastDate: String,
    )
}

@Serializable
data class PerfStat(
    val user: PerfStatUser,
    val perf: PerfStatPerf,
    val rank: Long?,
    val percentile: Double,
    val stat: PerfStatStat,
)
