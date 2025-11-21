package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient

/**
 * Service for External engine API endpoints
 * Provides methods to interact with Lichess external engine data
 */
class ExternalEngineService(
    private val apiClient: BaseApiClient,
) {
    /**
     * List external engines
     * Lists all external engines that have been registered for the user,
     * and the credentials required to use them.
     */
    suspend fun externalEngineList(): Result<List<ExternalEngine>> {
        return try {
            val result: List<ExternalEngine> = apiClient.safeGet("api/external-engine")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Create external engine
     * Registers a new external engine for the user. It can then be selected
     * and used on the analysis board.
     */
    suspend fun externalEngineCreate(body: ExternalEngineRegistration): Result<ExternalEngine> {
        return try {
            val result: ExternalEngine = apiClient.safePost("api/external-engine", body = body)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get external engine
     * Get properties and credentials of an external engine.
     */
    suspend fun externalEngineGet(id: String): Result<ExternalEngine> {
        return try {
            val result: ExternalEngine = apiClient.safeGet("api/external-engine/$id")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Update external engine
     * Updates the properties of an external engine.
     */
    suspend fun externalEnginePut(
        id: String,
        body: ExternalEngineRegistration,
    ): Result<ExternalEngine> {
        return try {
            val result: ExternalEngine = apiClient.safePut("api/external-engine/$id", body = body)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Delete external engine
     * Unregisters an external engine.
     */
    suspend fun externalEngineDelete(id: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safeDelete("api/external-engine/$id")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Analyse with external engine
     * **Endpoint: `https://engine.lichess.ovh/api/external-engine/{id}/analyse`**
     * Request analysis from an external engine.
     */
    suspend fun externalEngineAnalyse(body: Map<String, Any>): Result<ExternalEngineAnalyseResponse> {
        return try {
            val result: ExternalEngineAnalyseResponse = apiClient.safePost("api/external-engine/work", body = body)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Acquire analysis request
     * **Endpoint: `https://engine.lichess.ovh/api/external-engine/work`**
     * Wait for an analysis requests to any of the external engines that
     */
    suspend fun externalEngineAcquire(body: Map<String, Any>): Result<ExternalengineacquireResponse> {
        return try {
            val result: ExternalengineacquireResponse = apiClient.safePost("api/external-engine/work", body = body)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Answer analysis request
     * **Endpoint: `https://engine.lichess.ovh/api/external-engine/work/{id}`**
     * Submit a stream of analysis as [UCI output](https://backscattering.de/chess/uci/#engine-info).
     */
    suspend fun externalEngineSubmit(body: String): Result<Unit> {
        return try {
            val result: Unit = apiClient.safePost("api/external-engine/work/{id}", body = body)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
