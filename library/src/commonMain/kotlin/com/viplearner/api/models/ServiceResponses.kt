package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class AccountPlayingResponse(
    val nowPlaying: List<NowPlayingGame>
)

@Serializable
data class NowPlayingGame(
    val fullId: String,
    val gameId: String,
    val fen: String,
    val color: GameColor,
    val lastMove: String,
    val source: GameSource,
    val variant: Variant,
    val speed: Speed,
    val perf: PerfType,
    val rated: Boolean,
    val hasMoved: Boolean,
    val opponent: NowPlayingOpponent,
    val isMyTurn: Boolean,
    val secondsLeft: Int,
    val status: GameStatusName? = null,
    val tournamentId: String? = null,
    val swissId: String? = null,
    val winner: GameColor? = null,
    val ratingDiff: Int? = null
)

@Serializable
data class NowPlayingOpponent(
    val id: String,
    val username: String,
    val rating: Int? = null,
    val ratingDiff: Int? = null,
    val ai: Int? = null
)

@Serializable
data class GameImportResponse(
    val id: String,
    val url: String
)

@Serializable
data class LiveStreamerResponse(
    val id: String,
    val name: String,
    val title: Title? = null,
    val patron: Boolean? = null,
    val flair: Flair? = null,
    val stream: LiveStream? = null,
    val streamer: StreamerInfo? = null
)

@Serializable
data class LiveStream(
    val service: String,
    val status: String,
    val lang: String? = null
)

@Serializable
data class StreamerInfo(
    val name: String? = null,
    val headline: String? = null,
    val description: String? = null,
    val twitch: String? = null,
    val youTube: String? = null,
    val image: String? = null
)

@Serializable
data class PlayerAutocompleteResponse(
    val result: List<LightUserOnline>
)

@Serializable
data class BoardSeekResponse(
    val id: String
)

@Serializable
data class ExternalEngineAnalyseResponse(
    val id: String,
    val work: ExternalEngineWork,
    val engine: ExternalEngine
)

@Serializable
data class TvChannelsResponse(
    val bot: TvGame,
    val blitz: TvGame,
    val racingKings: TvGame,
    val ultraBullet: TvGame,
    val bullet: TvGame,
    val classical: TvGame,
    val threeCheck: TvGame,
    val antichess: TvGame,
    val computer: TvGame,
    val horde: TvGame,
    val rapid: TvGame,
    val atomic: TvGame,
    val crazyhouse: TvGame,
    val chess960: TvGame,
    val kingOfTheHill: TvGame,
    val best: TvGame
)

@Serializable
data class TournamentTeamsResponse(
    val id: String,
    val teams: List<TournamentTeam>
)

@Serializable
data class TournamentTeam(
    val rank: Int,
    val id: String,
    val score: Int,
    val players: List<TournamentTeamPlayer>
)

@Serializable
data class TournamentTeamPlayer(
    val user: LightUser,
    val score: Int? = null
)

