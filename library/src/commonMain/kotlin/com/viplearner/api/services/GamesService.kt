package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.AccountPlayingResponse
import com.viplearner.api.models.GameImportResponse
import com.viplearner.api.models.GameJson
import com.viplearner.api.models.GameStream
import com.viplearner.api.models.MoveStream
import com.viplearner.api.models.Ok
import kotlinx.coroutines.flow.Flow

/**
 * Service for Games API endpoints
 * Provides methods to interact with Lichess games data
 */
class GamesService(
    private val apiClient: BaseApiClient,
) {
    /**
     * Export one game as JSON
     * Download one game in JSON format.
     * Ongoing games are delayed by a few seconds ranging from 3 to 60 depending on the time control, as to prevent cheat bots from using this API.
     *
     * @param gameId The game ID (8 characters)
     * @param moves Include the PGN moves (default: true)
     * @param pgnInJson Include the full PGN within the JSON response, in a `pgn` field (default: false)
     * @param tags Include the PGN tags (default: true)
     * @param clocks Include clock status when available (default: true)
     * @param evals Include analysis evaluations and comments, when available (default: true)
     * @param accuracy Include accuracy percent of each player, when available (default: false)
     * @param opening Include the opening name (default: true)
     * @param division Plies which mark the beginning of the middlegame and endgame (default: true)
     * @param literate Insert textual annotations in the PGN (default: false)
     * @param withBookmarked Add a `bookmarked: true` field when the logged in user has bookmarked the game (default: false)
     * @return The game representation in JSON format
     */
    suspend fun gamePgnAsJson(
        gameId: String,
        moves: Boolean? = null,
        pgnInJson: Boolean? = null,
        tags: Boolean? = null,
        clocks: Boolean? = null,
        evals: Boolean? = null,
        accuracy: Boolean? = null,
        opening: Boolean? = null,
        division: Boolean? = null,
        literate: Boolean? = null,
        withBookmarked: Boolean? = null,
    ): Result<GameJson> {
        return try {
            val queryParams =
                mapOf(
                    "moves" to moves,
                    "pgnInJson" to pgnInJson,
                    "tags" to tags,
                    "clocks" to clocks,
                    "evals" to evals,
                    "accuracy" to accuracy,
                    "opening" to opening,
                    "division" to division,
                    "literate" to literate,
                    "withBookmarked" to withBookmarked,
                ).filterValues { it != null }
            val result: GameJson = apiClient.safeGet("game/export/$gameId", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export one game as PGN
     * Download one game in PGN format (text).
     * Ongoing games are delayed by a few seconds ranging from 3 to 60 depending on the time control, as to prevent cheat bots from using this API.
     *
     * @param gameId The game ID (8 characters)
     * @param moves Include the PGN moves (default: true)
     * @param tags Include the PGN tags (default: true)
     * @param clocks Include clock status when available as PGN comments (default: true)
     * @param evals Include analysis evaluations and comments as PGN comments, when available (default: true)
     * @param opening Include the opening name (default: true)
     * @param literate Insert textual annotations in the PGN about the opening, analysis variations, mistakes, and game termination (default: false)
     * @return The game representation in PGN format (text)
     */
    suspend fun gamePgnAsPgn(
        gameId: String,
        moves: Boolean? = null,
        tags: Boolean? = null,
        clocks: Boolean? = null,
        evals: Boolean? = null,
        opening: Boolean? = null,
        literate: Boolean? = null,
    ): Result<String> {
        return try {
            val queryParams =
                mapOf(
                    "moves" to moves,
                    "tags" to tags,
                    "clocks" to clocks,
                    "evals" to evals,
                    "opening" to opening,
                    "literate" to literate,
                ).filterValues { it != null }
            val result: String = apiClient.safeGetPgn("game/export/$gameId", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export ongoing game of a user as JSON
     * Download the ongoing game, or the last game played, of a user in JSON format.
     *
     * @param username The username
     * @param moves Include the PGN moves (default: true)
     * @param pgnInJson Include the full PGN within the JSON response, in a `pgn` field (default: false)
     * @param tags Include the PGN tags (default: true)
     * @param clocks Include clock status when available (default: true)
     * @param evals Include analysis evaluations and comments, when available (default: true)
     * @param accuracy Include accuracy percent of each player, when available (default: false)
     * @param opening Include the opening name (default: true)
     * @param division Plies which mark the beginning of the middlegame and endgame (default: true)
     * @param literate Insert textual annotations in the PGN (default: false)
     * @return The game representation in JSON format
     */
    suspend fun userCurrentGameAsJson(
        username: String,
        moves: Boolean? = null,
        pgnInJson: Boolean? = null,
        tags: Boolean? = null,
        clocks: Boolean? = null,
        evals: Boolean? = null,
        accuracy: Boolean? = null,
        opening: Boolean? = null,
        division: Boolean? = null,
        literate: Boolean? = null,
    ): Result<GameJson> {
        return try {
            val queryParams =
                mapOf(
                    "moves" to moves,
                    "pgnInJson" to pgnInJson,
                    "tags" to tags,
                    "clocks" to clocks,
                    "evals" to evals,
                    "accuracy" to accuracy,
                    "opening" to opening,
                    "division" to division,
                    "literate" to literate,
                ).filterValues { it != null }
            val result: GameJson = apiClient.safeGet("api/user/$username/current-game", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export ongoing game of a user as PGN
     * Download the ongoing game, or the last game played, of a user in PGN format (text).
     *
     * @param username The username
     * @param moves Include the PGN moves (default: true)
     * @param tags Include the PGN tags (default: true)
     * @param clocks Include clock status when available as PGN comments (default: true)
     * @param evals Include analysis evaluations and comments as PGN comments, when available (default: true)
     * @param opening Include the opening name (default: true)
     * @param literate Insert textual annotations in the PGN about the opening, analysis variations, mistakes, and game termination (default: false)
     * @return The game representation in PGN format (text)
     */
    suspend fun userCurrentGameAsPgn(
        username: String,
        moves: Boolean? = null,
        tags: Boolean? = null,
        clocks: Boolean? = null,
        evals: Boolean? = null,
        opening: Boolean? = null,
        literate: Boolean? = null,
    ): Result<String> {
        return try {
            val queryParams =
                mapOf(
                    "moves" to moves,
                    "tags" to tags,
                    "clocks" to clocks,
                    "evals" to evals,
                    "opening" to opening,
                    "literate" to literate,
                ).filterValues { it != null }
            val result: String = apiClient.safeGetPgn("api/user/$username/current-game", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export games of a user
     * Download all games of any user in PGN or [ndjson](#section/Introduction/Streaming-with-ND-JSON) format.
     * Games are sorted by reverse chronological order (most recent first).
     */
    suspend fun gamesUser(
        username: String,
        since: Int? = null,
        until: Int? = null,
        max: Int? = null,
        vs: String? = null,
        rated: Boolean? = null,
        perfType: String? = null,
        color: String? = null,
        analysed: Boolean? = null,
        moves: Boolean? = null,
        pgnInJson: Boolean? = null,
        tags: Boolean? = null,
        clocks: Boolean? = null,
        evals: Boolean? = null,
        accuracy: Boolean? = null,
        opening: Boolean? = null,
        division: Boolean? = null,
        ongoing: Boolean? = null,
        finished: Boolean? = null,
        literate: Boolean? = null,
        lastFen: Boolean? = null,
        withBookmarked: Boolean? = null,
        sort: String? = null,
    ): Result<Flow<GameJson>> {
        return try {
            val queryParams =
                mapOf(
                    "since" to since,
                    "until" to until,
                    "max" to max,
                    "vs" to vs,
                    "rated" to rated,
                    "perfType" to perfType,
                    "color" to color,
                    "analysed" to analysed,
                    "moves" to moves,
                    "pgnInJson" to pgnInJson,
                    "tags" to tags,
                    "clocks" to clocks,
                    "evals" to evals,
                    "accuracy" to accuracy,
                    "opening" to opening,
                    "division" to division,
                    "ongoing" to ongoing,
                    "finished" to finished,
                    "literate" to literate,
                    "lastFen" to lastFen,
                    "withBookmarked" to withBookmarked,
                    "sort" to sort,
                ).filterValues { it != null }
            val result: Flow<GameJson> = apiClient.safeNdjsonGet("api/games/user/$username", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export games by IDs
     * Download games by IDs in PGN or [ndjson](#section/Introduction/Streaming-with-ND-JSON) format, depending on the request `Accept` header.
     * Games are sorted by reverse chronological order (most recent first)
     */
    fun gamesExportIds(
        moves: Boolean? = null,
        pgnInJson: Boolean? = null,
        tags: Boolean? = null,
        clocks: Boolean? = null,
        evals: Boolean? = null,
        accuracy: Boolean? = null,
        opening: Boolean? = null,
        division: Boolean? = null,
        literate: Boolean? = null,
        body: String,
    ): Result<Flow<GameJson>> {
        return try {
            val queryParams =
                mapOf(
                    "moves" to moves,
                    "pgnInJson" to pgnInJson,
                    "tags" to tags,
                    "clocks" to clocks,
                    "evals" to evals,
                    "accuracy" to accuracy,
                    "opening" to opening,
                    "division" to division,
                    "literate" to literate,
                ).filterValues { it != null }
            val result: Flow<GameJson> = apiClient.safeNdjsonPost("api/games/export/_ids", queryParams, body)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Stream games of users
     * Stream the games played between a list of users, in real time.
     * Only games where **both players** are part of the list are included.
     */
    suspend fun gamesByUsers(
        withCurrentGames: Boolean? = null,
        body: String,
    ): Result<Flow<GameStream>> {
        return try {
            val queryParams =
                mapOf(
                    "withCurrentGames" to withCurrentGames,
                ).filterValues { it != null }
            val result: Flow<GameStream> = apiClient.safeNdjsonPost("api/stream/games-by-users", queryParams, body)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Stream games by IDs
     * Creates a stream of games from an arbitrary streamId, and a list of game IDs.
     * The stream first outputs the games that already exists, then emits an event each time a game is started or finished.
     */
    suspend fun gamesByIds(
        streamId: String,
        body: String,
    ): Result<Flow<GameStream>> {
        return try {
            val result: Flow<GameStream> = apiClient.safeNdjsonPost("api/stream/games/$streamId", body = body)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Add game IDs to stream
     * Add new game IDs for [an existing stream](#operation/gamesByIds) to watch.
     * The stream will immediately outputs the games that already exists, then emit an event each time a game is started or finished.
     */
    suspend fun gamesByIdsAdd(
        streamId: String,
        body: String,
    ): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/stream/games/$streamId/add", body = body)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get my ongoing games
     * Get the ongoing games of the current user.
     * Real-time and correspondence games are included.
     */
    suspend fun accountPlaying(nb: Int? = null): Result<AccountPlayingResponse> {
        return try {
            val queryParams =
                mapOf(
                    "nb" to nb,
                ).filterValues { it != null }
            val result: AccountPlayingResponse = apiClient.safeGet("api/account/playing", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Stream moves of a game
     * Stream positions and moves of any ongoing game, in [ndjson](#section/Introduction/Streaming-with-ND-JSON).
     * A description of the game is sent as a first message.
     */
    suspend fun streamGame(id: String): Result<Flow<MoveStream>> {
        return try {
            val result: Flow<MoveStream> = apiClient.safeNdjsonGet("api/stream/game/$id")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Import one game
     * Import a game from PGN. See <https://lichess.org/paste>.
     * Rate limiting: 200 games per hour for OAuth requests, 100 games per hour for anonymous requests.
     */
    suspend fun gameImport(pgn: String? = null): Result<GameImportResponse> {
        return try {
            val formBody =
                mapOf(
                    "pgn" to pgn,
                ).filterValues { it != null }
            val result: GameImportResponse = apiClient.safePost("api/import", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export your imported games
     * Download all games imported by you. Games are exported in PGN format.
     */
    suspend fun importedGamesUser(): Result<String> {
        return try {
            val result: String = apiClient.safeGetPgn("api/games/export/imports")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export your bookmarked games
     * Download all games bookmarked by you, in PGN or [ndjson](#section/Introduction/Streaming-with-ND-JSON) format.
     * Games are sorted by reverse chronological order (most recent first).
     */
    suspend fun exportBookmarks(
        since: Int? = null,
        until: Int? = null,
        max: Int? = null,
        moves: Boolean? = null,
        pgnInJson: Boolean? = null,
        tags: Boolean? = null,
        clocks: Boolean? = null,
        evals: Boolean? = null,
        accuracy: Boolean? = null,
        opening: Boolean? = null,
        division: Boolean? = null,
        literate: Boolean? = null,
        lastFen: Boolean? = null,
        sort: String? = null,
    ): Result<Flow<GameJson>> {
        return try {
            val queryParams =
                mapOf(
                    "since" to since,
                    "until" to until,
                    "max" to max,
                    "moves" to moves,
                    "pgnInJson" to pgnInJson,
                    "tags" to tags,
                    "clocks" to clocks,
                    "evals" to evals,
                    "accuracy" to accuracy,
                    "opening" to opening,
                    "division" to division,
                    "literate" to literate,
                    "lastFen" to lastFen,
                    "sort" to sort,
                ).filterValues { it != null }
            val result: Flow<GameJson> = apiClient.safeNdjsonGet("api/games/export/bookmarks", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
