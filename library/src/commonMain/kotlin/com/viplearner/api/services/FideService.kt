package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for FIDE API endpoints
 * Provides methods to interact with Lichess fide data
 */
class FideService(
    private val apiClient: BaseApiClient
) {

    /**
     * Get a FIDE player
     * Get information about a FIDE player.
     */
    suspend fun fidePlayerGet(playerId: Int): Result<FIDEPlayer> {
        return try {
            val result: FIDEPlayer = apiClient.safeGet("api/fide/player/${playerId}")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Search FIDE players
     * List of FIDE players search results for a query.
     */
    suspend fun fidePlayerSearch(q: String): Result<List<FIDEPlayer>> {
        return try {
            val queryParams = mapOf(
                "q" to q
            ).filterValues { it != null }
            val result: List<FIDEPlayer> = apiClient.safeGet("api/fide/player", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
