package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserActivityInterval(
    val start: Long,
    val end: Long,
)

@Serializable
data class UserActivityGames(
    val chess960: UserActivityScore? = null,
    val atomic: UserActivityScore? = null,
    val racingKings: UserActivityScore? = null,
    val ultraBullet: UserActivityScore? = null,
    val blitz: UserActivityScore? = null,
    val kingOfTheHill: UserActivityScore? = null,
    val bullet: UserActivityScore? = null,
    val correspondence: UserActivityScore? = null,
    val horde: UserActivityScore? = null,
    val puzzle: UserActivityScore? = null,
    val classical: UserActivityScore? = null,
    val rapid: UserActivityScore? = null,
)

@Serializable
data class UserActivityPuzzles(
    val score: UserActivityScore? = null,
)

@Serializable
data class UserActivityTournaments(
    val nb: Long? = null,
    val best: List<BestTournament>? = null,
) {
    @Serializable
    data class TournamentInfo(
        val id: String,
        val name: String,
    )

    @Serializable
    data class BestTournament(
        val tournament: TournamentInfo,
        val nbGames: Long,
        val score: Long,
        val rank: Long,
        val rankPercent: Long,
    )
}

@Serializable
data class UserActivityCorrespondencemoves(
    val nb: Long,
    val games: List<UserActivityCorrespondenceGame>,
)

@Serializable
data class UserActivityCorrespondenceends(
    val correspondence: CorrespondenceData,
) {
    @Serializable
    data class CorrespondenceData(
        val score: UserActivityScore,
        val games: List<UserActivityCorrespondenceGame>,
    )
}

@Serializable
data class UserActivityFollows(
    @SerialName("in")
    val `in`: UserActivityFollowList? = null,
    val out: UserActivityFollowList? = null,
)

@Serializable
data class UserActivityPatron(
    val months: Long,
)

@Serializable
data class UserActivity(
    val interval: UserActivityInterval,
    val games: UserActivityGames? = null,
    val puzzles: UserActivityPuzzles? = null,
    val storm: PuzzleModePerf? = null,
    val racer: PuzzleModePerf? = null,
    val streak: PuzzleModePerf? = null,
    val tournaments: UserActivityTournaments? = null,
    val practice: List<Practice>? = null,
    val simuls: List<String>? = null,
    val correspondenceMoves: UserActivityCorrespondencemoves? = null,
    val correspondenceEnds: UserActivityCorrespondenceends? = null,
    val follows: UserActivityFollows? = null,
    val studies: List<Study>? = null,
    val teams: List<Team>? = null,
    val posts: List<Post>? = null,
    val patron: UserActivityPatron? = null,
    val stream: Boolean? = null,
) {
    @Serializable
    data class Study(
        val id: String,
        val name: String,
    )

    @Serializable
    data class Team(
        val url: String,
        val name: String,
        val flair: Flair? = null,
    )

    @Serializable
    data class Post(
        val topicUrl: String,
        val topicName: String,
        val posts: List<PostDetail>,
    ) {
        @Serializable
        data class PostDetail(
            val url: String,
            val text: String,
        )
    }

    @Serializable
    data class Practice(
        val url: String,
        val name: String,
        val nbPositions: Long,
    )
}
