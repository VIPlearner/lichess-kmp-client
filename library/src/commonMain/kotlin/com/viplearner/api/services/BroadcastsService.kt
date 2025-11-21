package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import kotlinx.coroutines.flow.Flow

/**
 * Service for Broadcasts API endpoints
 * Provides methods to interact with Lichess broadcasts data
 */
class BroadcastsService(
    private val apiClient: BaseApiClient,
) {
    /**
     * Get official broadcasts
     * Returns ongoing official broadcasts sorted by tier.
     * After that, returns finished broadcasts sorted by most recent sync time.
     */
    suspend fun broadcastsOfficial(
        nb: Int? = null,
        html: Boolean? = null,
    ): Result<Flow<BroadcastWithRounds>> {
        return try {
            val queryParams =
                mapOf(
                    "nb" to nb,
                    "html" to html,
                ).filterValues { it != null }
            val result: Flow<BroadcastWithRounds> = apiClient.safeNdjsonGet("api/broadcast", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get paginated top broadcast previews
     * The same data, in the same order, as can be seen on [https://lichess.org/broadcast](/broadcast).
     */
    suspend fun broadcastsTop(
        page: Int? = null,
        html: Boolean? = null,
    ): Result<BroadcastTop> {
        return try {
            val queryParams =
                mapOf(
                    "page" to page,
                    "html" to html,
                ).filterValues { it != null }
            val result: BroadcastTop = apiClient.safeGet("api/broadcast/top", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get broadcasts created by a user
     * Get all incoming, ongoing, and finished official broadcasts.
     * The broadcasts are sorted by created date, most recent first.
     */
    suspend fun broadcastsByUser(
        username: String,
        page: Int? = null,
        html: Boolean? = null,
    ): Result<BroadcastsbyuserResponse> {
        return try {
            val queryParams =
                mapOf(
                    "page" to page,
                    "html" to html,
                ).filterValues { it != null }
            val result: BroadcastsbyuserResponse = apiClient.safeGet("api/broadcast/by/$username", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Search broadcasts
     * Search across recent official broadcasts.
     */
    suspend fun broadcastsSearch(
        page: Int? = null,
        q: String? = null,
    ): Result<BroadcastssearchResponse> {
        return try {
            val queryParams =
                mapOf(
                    "page" to page,
                    "q" to q,
                ).filterValues { it != null }
            val result: BroadcastssearchResponse = apiClient.safeGet("api/broadcast/search", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Create a broadcast tournament
     * Create a new broadcast tournament to relay external games.
     * This endpoint accepts the same form data as the [web form](https://lichess.org/broadcast/new).
     */
    suspend fun broadcastTourCreate(formData: Map<String, String>): Result<BroadcastWithRounds> {
        return try {
            val result: BroadcastWithRounds = apiClient.safePost("broadcast/new", body = formData)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get a broadcast tournament
     * Get information about a broadcast tournament.
     */
    suspend fun broadcastTourGet(broadcastTournamentId: String): Result<BroadcastWithRounds> {
        return try {
            val result: BroadcastWithRounds = apiClient.safeGet("api/broadcast/$broadcastTournamentId")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get players of a broadcast
     * Get the list of players of a broadcast tournament, if available.
     */
    suspend fun broadcastPlayersGet(broadcastTournamentId: String): Result<List<BroadcastPlayerEntry>> {
        return try {
            val result: List<BroadcastPlayerEntry> = apiClient.safeGet("broadcast/$broadcastTournamentId/players")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get a player from a broadcast
     * Get the details of a specific player and their games from a broadcast tournament.
     */
    suspend fun broadcastPlayerGet(
        broadcastTournamentId: String,
        playerId: String,
    ): Result<BroadcastPlayerEntryWithFideAndGames> {
        return try {
            val result: BroadcastPlayerEntryWithFideAndGames =
                apiClient.safeGet(
                    "broadcast/$broadcastTournamentId/players/$playerId",
                )
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Update your broadcast tournament
     * Update information about a broadcast tournament that you created.
     * This endpoint accepts the same form data as the web form.
     */
    suspend fun broadcastTourUpdate(
        broadcastTournamentId: String,
        formData: Map<String, String>,
    ): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("broadcast/$broadcastTournamentId/edit", body = formData)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Create a broadcast round
     * Create a new broadcast round to relay external games.
     * This endpoint accepts the same form data as the web form.
     */
    suspend fun broadcastRoundCreate(
        broadcastTournamentId: String,
        formData: Map<String, String>,
    ): Result<BroadcastRoundNew> {
        return try {
            val result: BroadcastRoundNew = apiClient.safePost("broadcast/$broadcastTournamentId/new", body = formData)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get a broadcast round
     * Get information about a broadcast round.
     */
    suspend fun broadcastRoundGet(
        broadcastTournamentSlug: String,
        broadcastRoundSlug: String,
        broadcastRoundId: String,
    ): Result<BroadcastRound> {
        return try {
            val result: BroadcastRound =
                apiClient.safeGet(
                    "api/broadcast/$broadcastTournamentSlug/$broadcastRoundSlug/$broadcastRoundId",
                )
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Update a broadcast round
     * Update information about a broadcast round.
     * This endpoint accepts the same form data as the web form.
     */
    suspend fun broadcastRoundUpdate(
        broadcastRoundId: String,
        formData: Map<String, String>,
    ): Result<BroadcastRound> {
        return try {
            val result: BroadcastRound = apiClient.safePost("broadcast/round/$broadcastRoundId/edit", body = formData)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Reset a broadcast round
     * Remove any games from the broadcast round and reset it to its initial state.
     */
    suspend fun broadcastRoundReset(broadcastRoundId: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/broadcast/round/$broadcastRoundId/reset")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Push PGN to a broadcast round
     * Update a broadcast with new PGN.
     * Only for broadcasts without a source URL.
     */
    suspend fun broadcastPush(
        broadcastRoundId: String,
        body: String,
    ): Result<BroadcastPgnPush> {
        return try {
            val result: BroadcastPgnPush = apiClient.safePost("api/broadcast/round/$broadcastRoundId/push", body = body)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Stream an ongoing broadcast round as PGN
     * This streaming endpoint first sends all games of a broadcast round in PGN format.
     * Then, it waits for new moves to be played. As soon as it happens, the entire PGN of the game is sent to the stream.
     */
    suspend fun broadcastStreamRoundPgn(broadcastRoundId: String): Result<String> {
        return try {
            val result: String = apiClient.safeGetPgn("api/stream/broadcast/round/$broadcastRoundId.pgn")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export one round as PGN
     * Download all games of a single round of a broadcast tournament in PGN format.
     * You *could* poll this endpoint to get updates about a tournament, but it would be slow,
     */
    suspend fun broadcastRoundPgn(broadcastRoundId: String): Result<String> {
        return try {
            val result: String = apiClient.safeGetPgn("api/broadcast/round/$broadcastRoundId.pgn")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export all rounds as PGN
     * Download all games of all rounds of a broadcast in PGN format.
     * If a `study:read` [OAuth token](#tag/OAuth) is provided,
     */
    suspend fun broadcastAllRoundsPgn(broadcastTournamentId: String): Result<String> {
        return try {
            val result: String = apiClient.safeGetPgn("api/broadcast/$broadcastTournamentId.pgn")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get your broadcast rounds
     * Stream all broadcast rounds you are a member of.
     * Also includes broadcasts rounds you did not create, but were invited to.
     */
    suspend fun broadcastMyRoundsGet(nb: Int? = null): Result<Flow<BroadcastMyRound>> {
        return try {
            val queryParams =
                mapOf(
                    "nb" to nb,
                ).filterValues { it != null }
            val result: Flow<BroadcastMyRound> = apiClient.safeNdjsonGet("api/broadcast/my-rounds", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
