package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.AccountPreferencesResponse
import com.viplearner.api.models.AccountemailResponse
import com.viplearner.api.models.AccountkidResponse
import com.viplearner.api.models.Ok
import com.viplearner.api.models.Timeline
import com.viplearner.api.models.UserExtended

/**
 * Service for Account API endpoints
 * Provides methods to interact with Lichess account data
 */
class AccountService(
    private val apiClient: BaseApiClient,
) {
    /**
     * Get my profile
     * Public information about the logged in user.
     */
    suspend fun accountMe(): Result<UserExtended> {
        return try {
            val result: UserExtended = apiClient.safeGet("api/account")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get my email address
     * Read the email address of the logged in user.
     */
    suspend fun accountEmail(): Result<AccountemailResponse> {
        return try {
            val result: AccountemailResponse = apiClient.safeGet("api/account/email")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get my preferences
     * Read the preferences of the logged in user.
     * - <https://lichess.org/account/preferences/game-display>
     */
    suspend fun account(): Result<AccountPreferencesResponse> {
        return try {
            val result: AccountPreferencesResponse = apiClient.safeGet("api/account/preferences")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get my kid mode status
     * Read the kid mode status of the logged in user.
     * - <https://lichess.org/account/kid>
     */
    suspend fun accountKid(): Result<AccountkidResponse> {
        return try {
            val result: AccountkidResponse = apiClient.safeGet("api/account/kid")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Set my kid mode status
     * Set the kid mode status of the logged in user.
     * - <https://lichess.org/account/kid>
     */
    suspend fun accountKidPost(v: Boolean): Result<Ok> {
        return try {
            val queryParams =
                mapOf(
                    "v" to v,
                ).filterValues { it != null }
            val result: Ok = apiClient.safePost("api/account/kid", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get my timeline
     * Get the timeline events of the logged in user.
     */
    suspend fun timeline(
        since: Int? = null,
        nb: Int? = null,
    ): Result<Timeline> {
        return try {
            val queryParams =
                mapOf(
                    "since" to since,
                    "nb" to nb,
                ).filterValues { it != null }
            val result: Timeline = apiClient.safeGet("api/timeline", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
