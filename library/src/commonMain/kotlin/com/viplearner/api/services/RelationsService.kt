package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for Relations API endpoints
 * Provides methods to interact with Lichess relations data
 */
class RelationsService(
    private val apiClient: BaseApiClient
) {

    /**
     * Get users followed by the logged in user
     * Users are streamed as [ndjson](#section/Introduction/Streaming-with-ND-JSON).
     */
    suspend fun userFollowing(): Result<Flow<UserExtended>> {
        return try {
            val result: Flow<UserExtended> = apiClient.safeGet("api/rel/following")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Follow a player
     * Follow a player, adding them to your list of Lichess friends.
     */
    suspend fun followUser(username: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/rel/follow/${username}")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Unfollow a player
     * Unfollow a player, removing them from your list of Lichess friends.
     */
    suspend fun unfollowUser(username: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/rel/unfollow/${username}")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Block a player
     * Block a player, adding them to your list of blocked Lichess users.
     */
    suspend fun blockUser(username: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/rel/block/${username}")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Unblock a player
     * Unblock a player, removing them from your list of blocked Lichess users.
     */
    suspend fun unblockUser(username: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/rel/unblock/${username}")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
