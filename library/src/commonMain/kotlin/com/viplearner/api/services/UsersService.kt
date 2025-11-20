package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.Crosstable
import com.viplearner.api.models.Leaderboard
import com.viplearner.api.models.LiveStreamerResponse
import com.viplearner.api.models.Ok
import com.viplearner.api.models.PerfStat
import com.viplearner.api.models.PlayerAutocompleteResponse
import com.viplearner.api.models.RatingHistory
import com.viplearner.api.models.Top10s
import com.viplearner.api.models.User
import com.viplearner.api.models.UserActivity
import com.viplearner.api.models.UserExtended
import com.viplearner.api.models.UserNote
import com.viplearner.api.models.UserStatus

/**
 * Service for Users API endpoints
 * Provides methods to interact with Lichess users data
 */
class UsersService(
    private val apiClient: BaseApiClient,
) {
    /**
     * Get real-time users status
     * Read the `online`, `playing` and `streaming` flags of several users.
     * This API is very fast and cheap on lichess side.
     */
    suspend fun usersStatus(
        ids: String,
        withSignal: Boolean? = null,
        withGameIds: Boolean? = null,
        withGameMetas: Boolean? = null,
    ): Result<List<UserStatus>> {
        return try {
            val queryParams =
                mapOf(
                    "ids" to ids,
                    "withSignal" to withSignal,
                    "withGameIds" to withGameIds,
                    "withGameMetas" to withGameMetas,
                ).filterValues { it != null }
            val result: List<UserStatus> = apiClient.safeGet("api/users/status", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get all top 10
     * Get the top 10 players for each speed and variant.
     * See <https://lichess.org/player>.
     */
    suspend fun player(): Result<Top10s> {
        return try {
            val result: Top10s = apiClient.safeGet("api/player")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get one leaderboard
     * Get the leaderboard for a single speed or variant (a.k.a. `perfType`).
     * There is no leaderboard for correspondence or puzzles.
     */
    suspend fun playerTopNbPerfType(
        nb: Int,
        perfType: String,
    ): Result<Leaderboard> {
        return try {
            val result: Leaderboard = apiClient.safeGet("api/player/top/$nb/$perfType")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get user public data
     * Read public data of a user.
     */
    suspend fun user(
        username: String,
        trophies: Boolean? = null,
    ): Result<UserExtended> {
        return try {
            val queryParams =
                mapOf(
                    "trophies" to trophies,
                ).filterValues { it != null }
            val result: UserExtended = apiClient.safeGet("api/user/$username", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get rating history of a user
     * Read rating history of a user, for all perf types.
     * There is at most one entry per day.
     */
    suspend fun userRatingHistory(username: String): Result<RatingHistory> {
        return try {
            val result: RatingHistory = apiClient.safeGet("api/user/$username/rating-history")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get performance statistics of a user
     * Read performance statistics of a user, for a single performance.
     * Similar to the [performance pages on the website](https://lichess.org/@/thibault/perf/bullet).
     */
    suspend fun userPerf(
        username: String,
        perf: String,
    ): Result<PerfStat> {
        return try {
            val result: PerfStat = apiClient.safeGet("api/user/$username/perf/$perf")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get user activity
     * Read data to generate the activity feed of a user.
     */
    suspend fun userActivity(username: String): Result<List<UserActivity>> {
        return try {
            val result: List<UserActivity> = apiClient.safeGet("api/user/$username/activity")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get users by ID
     * Get up to 300 users by their IDs. Users are returned in the same order as the IDs.
     * The method is `POST` to allow a longer list of IDs to be sent in the request body.
     */
    suspend fun users(body: String): Result<List<User>> {
        return try {
            val result: List<User> = apiClient.safePost("api/users", body = body)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get live streamers
     * Get basic info about currently streaming users.
     * This API is very fast and cheap on lichess side.
     */
    suspend fun streamerLive(): Result<List<LiveStreamerResponse>> {
        return try {
            val result: List<LiveStreamerResponse> = apiClient.safeGet("api/streamer/live")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get crosstable
     * Get total number of games, and current score, of any two users.
     * If the `matchup` flag is provided, and the users are currently playing, also gets the current match game number and scores.
     */
    suspend fun crosstable(
        user1: String,
        user2: String,
        matchup: Boolean? = null,
    ): Result<Crosstable> {
        return try {
            val queryParams =
                mapOf(
                    "matchup" to matchup,
                ).filterValues { it != null }
            val result: Crosstable = apiClient.safeGet("api/crosstable/$user1/$user2", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Autocomplete usernames
     * Provides autocompletion options for an incomplete username.
     */
    suspend fun playerAutocomplete(
        term: String,
        `object`: Boolean? = null,
        names: Boolean? = null,
        friend: Boolean? = null,
        team: String? = null,
        tour: String? = null,
        swiss: String? = null,
    ): Result<PlayerAutocompleteResponse> {
        return try {
            val queryParams =
                mapOf(
                    "term" to term,
                    "object" to `object`,
                    "names" to names,
                    "friend" to friend,
                    "team" to team,
                    "tour" to tour,
                    "swiss" to swiss,
                ).filterValues { it != null }
            val result: PlayerAutocompleteResponse = apiClient.safeGet("api/player/autocomplete", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Add a note for a user
     * Add a private note available only to you about this account.
     */
    suspend fun writeNote(
        username: String,
        text: String,
    ): Result<Ok> {
        return try {
            val formBody =
                mapOf(
                    "text" to text,
                ).filterValues { it != null }
            val result: Ok = apiClient.safePost("api/user/$username/note", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get notes for a user
     * Get the private notes that you have added for a user.
     */
    suspend fun readNote(username: String): Result<List<UserNote>> {
        return try {
            val result: List<UserNote> = apiClient.safeGet("api/user/$username/note")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
