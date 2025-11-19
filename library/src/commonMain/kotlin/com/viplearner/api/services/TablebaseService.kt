package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for Tablebase API endpoints
 * Provides methods to interact with Lichess tablebase data
 */
class TablebaseService(
    private val apiClient: BaseApiClient
) {

    /**
     * Tablebase lookup
     * **Endpoint: <https://tablebase.lichess.ovh>**
     */
    suspend fun tablebaseStandard(fen: String, dtc: String? = null): Result<TablebaseJson> {
        return try {
            val queryParams = mapOf(
                "fen" to fen,
                "dtc" to dtc
            ).filterValues { it != null }
            val result: TablebaseJson = apiClient.safeGet("standard", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Tablebase lookup for Atomic chess
     * **Endpoint: <https://tablebase.lichess.ovh>**
     */
    suspend fun tablebaseAtomic(fen: String): Result<TablebaseJson> {
        return try {
            val queryParams = mapOf(
                "fen" to fen
            ).filterValues { it != null }
            val result: TablebaseJson = apiClient.safeGet("atomic", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Tablebase lookup for Antichess
     * **Endpoint: <https://tablebase.lichess.ovh>**
     */
    suspend fun antichessAtomic(fen: String): Result<TablebaseJson> {
        return try {
            val queryParams = mapOf(
                "fen" to fen
            ).filterValues { it != null }
            val result: TablebaseJson = apiClient.safeGet("antichess", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
