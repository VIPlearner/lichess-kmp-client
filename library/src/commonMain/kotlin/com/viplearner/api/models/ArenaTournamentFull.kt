package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ArenaTournamentFullSpotlight(
    val headline: String? = null,
)

@Serializable
data class ArenaTournamentFullQuote(
    val text: String? = null,
    val author: String? = null,
)

@Serializable
data class ArenaTournamentFullGreatplayer(
    val name: String? = null,
    val url: String? = null,
)

@Serializable
data class ArenaTournamentFullMinratedgames(
    val nb: Long? = null,
)

@Serializable
data class ArenaTournamentFullPerf(
    val icon: String,
    val key: String,
    val name: String,
)

@Serializable
data class ArenaTournamentFullSchedule(
    val freq: String,
    val speed: String,
)

@Serializable
data class ArenaTournamentFullStanding(
    val page: Long? = null,
    val players: List<Player> = emptyList(),
) {
    @Serializable
    data class Player(
        val name: String? = null,
        val title: Title? = null,
        val patron: Boolean? = null,
        val patronColor: PatronColor? = null,
        val flair: Flair? = null,
        val rank: Long? = null,
        val rating: Long? = null,
        val score: Long? = null,
        val sheet: ArenaSheet? = null,
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
    val c: LiveClock? = null,
) {
    @Serializable
    data class LiveClock(
        val white: Long, // in seconds
        val black: Long, // in seconds
    )

    @Serializable
    data class Player(
        val name: String? = null,
        val id: String? = null,
        val rank: Long? = null,
        val rating: Long? = null,
    )
}

@Serializable
data class ArenaTournamentFullStats(
    val games: Long,
    val moves: Long,
    val whiteWins: Long,
    val blackWins: Long,
    val draws: Long,
    val berserks: Long,
    val averageRating: Long,
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
    val minutes: Long? = null,
    val createdBy: String? = null,
    val system: String? = null,
    val secondsToStart: Long? = null,
    val secondsToFinish: Long? = null,
    val isFinished: Boolean? = null,
    val isRecentlyFinished: Boolean? = null,
    val pairingsClosed: Boolean? = null,
    val startsAt: String? = null,
    val nbPlayers: Long,
    val verdicts: Verdicts? = null,
    val quote: ArenaTournamentFullQuote? = null,
    val greatPlayer: ArenaTournamentFullGreatplayer? = null,
    val allowList: List<String>? = null,
    val hasMaxRating: Boolean? = null,
    val maxRating: ArenaRatingObj? = null,
    val minRating: ArenaRatingObj? = null,
    val minRatedGames: ArenaTournamentFullMinratedgames? = null,
    val botsAllowed: Boolean? = null,
    val minAccountAgeInDays: Long? = null,
    val perf: ArenaTournamentFullPerf? = null,
    val schedule: ArenaTournamentFullSchedule? = null,
    val description: String? = null,
    val variant: String? = null,
    val duels: List<Duels>? = null,
    val standing: ArenaTournamentFullStanding? = null,
    val featured: ArenaTournamentFullFeatured? = null,
    val podium: List<Podium>? = null,
    val stats: ArenaTournamentFullStats? = null,
    val myUsername: String? = null,
) {
    @Serializable
    data class Duels(
        val id: String,
        val p: List<Player>,
    ) {
        @Serializable
        data class Player(
            val n: String,
            val r: Long,
            val k: Long,
        )
    }

    /**
     * "podium": {
     *             "type": "array",
     *             "items": {
     *               "type": "object",
     *               "properties": {
     *                 "name": {
     *                   "type": "string"
     *                 },
     *                 "title": {
     *                   "$ref": "#/components/schemas/Title"
     *                 },
     *                 "patron": {
     *                   "type": "boolean",
     *                   "deprecated": true
     *                 },
     *                 "patronColor": {
     *                   "$ref": "#/components/schemas/PatronColor"
     *                 },
     *                 "flair": {
     *                   "$ref": "#/components/schemas/Flair"
     *                 },
     *                 "rank": {
     *                   "type": "integer"
     *                 },
     *                 "rating": {
     *                   "type": "integer"
     *                 },
     *                 "score": {
     *                   "type": "integer"
     *                 },
     *                 "nb": {
     *                   "type": "object",
     *                   "properties": {
     *                     "game": {
     *                       "type": "integer"
     *                     },
     *                     "berserk": {
     *                       "type": "integer"
     *                     },
     *                     "win": {
     *                       "type": "integer"
     *                     }
     *                   }
     *                 },
     *                 "performance": {
     *                   "type": "integer"
     *                 }
     *               }
     *             }
     *           }
     */
    @Serializable
    data class Podium(
        val name: String? = null,
        val title: String? = null,
        val patron: Boolean? = null,
        val patronColor: PatronColor? = null,
        val flair: Flair? = null,
        val rank: Long? = null,
        val rating: Long? = null,
        val score: Long? = null,
        val nb: Nb? = null,
        val performance: Long? = null,
    ) {
        @Serializable
        data class Nb(
            val game: Long? = null,
            val berserk: Long? = null,
            val win: Long? = null,
        )
    }
}
