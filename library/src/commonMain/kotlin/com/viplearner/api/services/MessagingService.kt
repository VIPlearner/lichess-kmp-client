package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for Messaging API endpoints
 * Provides methods to interact with Lichess messaging data
 */
class MessagingService(
    private val apiClient: BaseApiClient
) {

    /**
     * Send a private message
     * Send a private message to another player.
     */
    suspend fun inboxUsername(username: String, text: String): Result<Ok> {
        return try {
            val formBody = mapOf(
                "text" to text
            ).filterValues { it != null }
            val result: Ok = apiClient.safePost("inbox/${username}", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
