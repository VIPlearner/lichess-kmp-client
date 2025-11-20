package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.CloudEval

/**
 * Service for Analysis API endpoints
 * Provides methods to interact with Lichess analysis data
 */
class AnalysisService(
    private val apiClient: BaseApiClient,
) {
    /**
     * Get cloud evaluation of a position.
     * Get the cached evaluation of a position, if available.
     * Opening positions have more chances of being available. There are about 15 million positions in the database.
     */
    suspend fun cloudEval(
        fen: String,
        multiPv: Int? = null,
        variant: String? = null,
    ): Result<CloudEval> {
        return try {
            val queryParams =
                mapOf(
                    "fen" to fen,
                    "multiPv" to multiPv,
                    "variant" to variant,
                ).filterValues { it != null }
            val result: CloudEval = apiClient.safeGet("api/cloud-eval", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
