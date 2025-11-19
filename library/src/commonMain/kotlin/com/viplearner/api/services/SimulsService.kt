package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for Simuls API endpoints
 * Provides methods to interact with Lichess simuls data
 */
class SimulsService(
    private val apiClient: BaseApiClient
) {

    /**
     * Get current simuls
     * Get recently created, started, finished, simuls.
     * Created and finished simul lists are not exhaustives, only those with
     */
    suspend fun simul(): Result<SimulResponse> {
        return try {
            val result: SimulResponse = apiClient.safeGet("api/simul")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
