package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for Bulk pairings API endpoints
 * Provides methods to interact with Lichess bulk pairings data
 */
class BulkPairingsService(
    private val apiClient: BaseApiClient,
) {
    /**
     * View your bulk pairings
     * Get a list of bulk pairings you created.
     */
    suspend fun bulkPairingList(): Result<List<BulkPairing>> {
        return try {
            val result: List<BulkPairing> = apiClient.safeGet("api/bulk-pairing")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Create a bulk pairing
     * Schedule many games at once, up to 24h in advance.
     * OAuth tokens are required for all paired players, with the `challenge:write` scope.
     */
    suspend fun bulkPairingCreate(
        players: String? = null,
        clockLimit: Int? = null,
        clockIncrement: Int? = null,
        days: Int? = null,
        pairAt: Int? = null,
        startClocksAt: Int? = null,
        rated: Boolean? = null,
        variant: String? = null,
        fen: String? = null,
        message: String? = null,
        rules: String? = null,
    ): Result<BulkPairing> {
        return try {
            val formBody =
                mapOf(
                    "players" to players,
                    "clock.limit" to clockLimit,
                    "clock.increment" to clockIncrement,
                    "days" to days,
                    "pairAt" to pairAt,
                    "startClocksAt" to startClocksAt,
                    "rated" to rated,
                    "variant" to variant,
                    "fen" to fen,
                    "message" to message,
                    "rules" to rules,
                ).filterValues { it != null }
            val result: BulkPairing = apiClient.safePost("api/bulk-pairing", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Manually start clocks
     * Immediately start all clocks of the games of a bulk pairing.
     * This overrides the `startClocksAt` value of an existing bulk pairing.
     */
    suspend fun bulkPairingStartClocks(id: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/bulk-pairing/$id/start-clocks")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Show a bulk pairing
     * Get a single bulk pairing by its ID.
     */
    suspend fun bulkPairingGet(id: String): Result<BulkPairing> {
        return try {
            val result: BulkPairing = apiClient.safeGet("api/bulk-pairing/$id")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Cancel a bulk pairing
     * Cancel and delete a bulk pairing that is scheduled in the future.
     * If the games have already been created, then this does nothing.
     */
    suspend fun bulkPairingDelete(id: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safeDelete("api/bulk-pairing/$id")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export games of a bulk pairing
     * Download games of a bulk in PGN or [ndjson](#section/Introduction/Streaming-with-ND-JSON) format, depending on the request `Accept` header.
     */
    suspend fun bulkPairingIdGamesGet(
        id: String,
        moves: Boolean? = null,
        pgnInJson: Boolean? = null,
        tags: Boolean? = null,
        clocks: Boolean? = null,
        evals: Boolean? = null,
        accuracy: Boolean? = null,
        opening: Boolean? = null,
        division: Boolean? = null,
        literate: Boolean? = null,
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
            val result: Flow<GameJson> = apiClient.safeNdjsonGet("api/bulk-pairing/$id/games", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
