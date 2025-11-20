package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for Teams API endpoints
 * Provides methods to interact with Lichess teams data
 */
class TeamsService(
    private val apiClient: BaseApiClient,
) {
    /**
     * Get team swiss tournaments
     * Get all swiss tournaments of a team.
     * Tournaments are sorted by reverse chronological order of start date (last starting first).
     */
    suspend fun teamSwiss(
        teamId: String,
        max: Int? = null,
        status: String? = null,
        createdBy: String? = null,
        name: String? = null,
    ): Result<Flow<SwissTournament>> {
        return try {
            val queryParams =
                mapOf(
                    "max" to max,
                    "status" to status,
                    "createdBy" to createdBy,
                    "name" to name,
                ).filterValues { it != null }
            val result: Flow<SwissTournament> = apiClient.safeNdjsonGet("api/team/$teamId/swiss", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get a single team
     * Public info about a team. Includes the list of publicly visible leaders.
     */
    suspend fun teamShow(teamId: String): Result<Team> {
        return try {
            val result: Team = apiClient.safeGet("api/team/$teamId")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get popular teams
     * Paginator of the most popular teams.
     */
    suspend fun teamAll(page: Int? = null): Result<TeamPaginatorJson> {
        return try {
            val queryParams =
                mapOf(
                    "page" to page,
                ).filterValues { it != null }
            val result: TeamPaginatorJson = apiClient.safeGet("api/team/all", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Teams of a player
     * All the teams a player is a member of.
     */
    suspend fun teamOfUsername(username: String): Result<List<Team>> {
        return try {
            val result: List<Team> = apiClient.safeGet("api/team/of/$username")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Search teams
     * Paginator of team search results for a keyword.
     */
    suspend fun teamSearch(
        text: String? = null,
        page: Int? = null,
    ): Result<TeamPaginatorJson> {
        return try {
            val queryParams =
                mapOf(
                    "text" to text,
                    "page" to page,
                ).filterValues { it != null }
            val result: TeamPaginatorJson = apiClient.safeGet("api/team/search", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get members of a team
     * Members are sorted by reverse chronological order of joining the team (most recent first).
     * OAuth is only required if the list of members is private.
     */
    suspend fun teamIdUsers(
        teamId: String,
        full: Boolean? = null,
    ): Result<Flow<TeamidusersResponse>> {
        return try {
            val queryParams =
                mapOf(
                    "full" to full,
                ).filterValues { it != null }
            val result: Flow<TeamidusersResponse> = apiClient.safeNdjsonGet("api/team/$teamId/users", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get team Arena tournaments
     * Get all Arena tournaments relevant to a team.
     * Tournaments are sorted by reverse chronological order of start date (last starting first).
     */
    suspend fun teamArena(
        teamId: String,
        max: Int? = null,
        status: String? = null,
        createdBy: String? = null,
        name: String? = null,
    ): Result<Flow<ArenaTournament>> {
        return try {
            val queryParams =
                mapOf(
                    "max" to max,
                    "status" to status,
                    "createdBy" to createdBy,
                    "name" to name,
                ).filterValues { it != null }
            val result: Flow<ArenaTournament> = apiClient.safeNdjsonGet("api/team/$teamId/arena", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Join a team
     * Join a team.
     * If the team requires a password but the `password` field is incorrect,
     */
    suspend fun teamIdJoin(
        teamId: String,
        message: String? = null,
        password: String? = null,
    ): Result<Ok> {
        return try {
            val formBody =
                mapOf(
                    "message" to message,
                    "password" to password,
                ).filterValues { it != null }
            val result: Ok = apiClient.safePost("team/$teamId/join", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Leave a team
     * Leave a team.
     * - <https://lichess.org/team>
     */
    suspend fun teamIdQuit(teamId: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("team/$teamId/quit")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get join requests
     * Get pending join requests of your team
     */
    suspend fun teamRequests(
        teamId: String,
        declined: Boolean? = null,
    ): Result<List<TeamRequestWithUser>> {
        return try {
            val queryParams =
                mapOf(
                    "declined" to declined,
                ).filterValues { it != null }
            val result: List<TeamRequestWithUser> = apiClient.safeGet("api/team/$teamId/requests", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Accept join request
     * Accept someone's request to join your team
     */
    suspend fun teamRequestAccept(
        teamId: String,
        userId: String,
    ): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/team/$teamId/request/$userId/accept")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Decline join request
     * Decline someone's request to join your team
     */
    suspend fun teamRequestDecline(
        teamId: String,
        userId: String,
    ): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/team/$teamId/request/$userId/decline")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Kick a user from your team
     * Kick a member out of one of your teams.
     * - <https://lichess.org/team>
     */
    suspend fun teamIdKickUserId(
        teamId: String,
        userId: String,
    ): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/team/$teamId/kick/$userId")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Message all members
     * Send a private message to all members of a team.
     * You must be a team leader with the "Messages" permission.
     */
    suspend fun teamIdPmAll(
        teamId: String,
        message: String? = null,
    ): Result<Ok> {
        return try {
            val formBody =
                mapOf(
                    "message" to message,
                ).filterValues { it != null }
            val result: Ok = apiClient.safePost("team/$teamId/pm-all", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
