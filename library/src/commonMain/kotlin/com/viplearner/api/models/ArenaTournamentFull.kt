package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ArenaTournamentFullSpotlight(
    val headline: String? = null
)

@Serializable
data class ArenaTournamentFullQuote(
    val text: String? = null,
    val author: String? = null
)

@Serializable
data class ArenaTournamentFullGreatplayer(
    val name: String? = null,
    val url: String? = null
)

@Serializable
data class ArenaTournamentFullMinratedgames(
    val nb: Int? = null
)

@Serializable
data class ArenaTournamentFullPerf(
    val icon: String,
    val key: String,
    val name: String
)

@Serializable
data class ArenaTournamentFullSchedule(
    val freq: String,
    val speed: String
)

@Serializable
data class ArenaTournamentFullStanding(
    val page: Int? = null,
    val players: List<Player> = emptyList()
) {
    @Serializable
    data class Player(
        val name: String? = null,
        val title: String? = null,
        val patron: Boolean? = null,
        val patronColor: String? = null,
        val flair: String? = null,
        val rank: Int? = null,
        val rating: Int? = null,
        val score: Int? = null,
        val sheet: ArenaSheet? = null
    )
}



@Serializable
data class ArenaTournamentFullFeatured(
    val id: String? = null,
    val fen: String? = null,
    val orientation: String? = null,
    val color: String? = null,
    val lastMove: String? = null,
    val white: Player? = null,
    val black: Player? = null,
    val c: LiveClock? = null
) {
    @Serializable
    data class LiveClock(
        val white: Int, //in seconds
        val black: Int, //in seconds
    )

    @Serializable
    data class Player(
        val name: String? = null,
        val id: String? = null,
        val rank: Int? = null,
        val rating: Int? = null
    )

}

@Serializable
data class ArenaTournamentFullStats(
    val games: Int,
    val moves: Int,
    val whiteWins: Int,
    val blackWins: Int,
    val draws: Int,
    val berserks: Int,
    val averageRating: Int
)

@Serializable
data class ArenaTournamentFull(
    val id: String,
    val fullName: String,
    val rated: Boolean? = null,
    val spotlight: ArenaTournamentFullSpotlight? = null,
    val berserkable: Boolean? = null,
    val onlyTitled: Boolean? = null,
    val clock: Clock,
    val minutes: Int? = null,
    val createdBy: String? = null,
    val system: String? = null,
    val secondsToStart: Int? = null,
    val secondsToFinish: Int? = null,
    val isFinished: Boolean? = null,
    val isRecentlyFinished: Boolean? = null,
    val pairingsClosed: Boolean? = null,
    val startsAt: String? = null,
    val nbPlayers: Int,
    val verdicts: Verdicts? = null,
    val quote: ArenaTournamentFullQuote? = null,
    val greatPlayer: ArenaTournamentFullGreatplayer? = null,
    val allowList: List<String>? = null,
    val hasMaxRating: Boolean? = null,
    val maxRating: ArenaRatingObj? = null,
    val minRating: ArenaRatingObj? = null,
    val minRatedGames: ArenaTournamentFullMinratedgames? = null,
    val botsAllowed: Boolean? = null,
    val minAccountAgeInDays: Int? = null,
    val perf: ArenaTournamentFullPerf? = null,
    val schedule: ArenaTournamentFullSchedule? = null,
    val description: String? = null,
    val variant: String? = null,
    val duels: List<Duels>? = null,
    val standing: ArenaTournamentFullStanding? = null,
    val featured: ArenaTournamentFullFeatured? = null,
    val podium: List<Podium>? = null,
    val stats: ArenaTournamentFullStats? = null,
    val myUsername: String? = null
) {
    @Serializable
    data class Duels(
        val id: String,
        val p: List<Player>
    ) {
        @Serializable
        data class Player(
            val n: String,
            val r: Int,
            val k: Int
        )
    }

    @Serializable
    data class Podium(
        val name: String? = null,
        val title: String? = null,
        val patron: Boolean? = null,
        val patronColor: String? = null,
        val flair: String? = null,
        val rank: Int? = null,
        val rating: Int? = null,
        val score: Int? = null,
        val nb: Nb? = null,
        val performance: Int? = null
    ) {
        @Serializable
        data class Nb(
            val game: Int? = null,
            val berserk: Int? = null,
            val win: Int? = null
        )
    }
}


