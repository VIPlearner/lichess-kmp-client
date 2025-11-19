package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for Challenges API endpoints
 * Provides methods to interact with Lichess challenges data
 */
class ChallengesService(
    private val apiClient: BaseApiClient
) {

    /**
     * List your challenges
     * Get a list of challenges created by or targeted at you.
     */
    suspend fun challengeList(): Result<ChallengelistResponse> {
        return try {
            val result: ChallengelistResponse = apiClient.safeGet("api/challenge")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Create a challenge
     * Challenge someone to play. The targeted player can choose to accept or decline.
     * If the challenge is accepted, you will be notified on the [event stream](#operation/apiStreamEvent)
     */
    suspend fun challengeCreate(username: String, formData: Map<String, String>): Result<ChallengeJson> {
        return try {
            val result: ChallengeJson = apiClient.safePost("api/challenge/${username}", body = formData)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Show one challenge
     * Get details about a challenge, even if it has been recently accepted, canceled or declined.
     */
    suspend fun challengeShow(challengeId: String): Result<ChallengeJson> {
        return try {
            val result: ChallengeJson = apiClient.safeGet("api/challenge/${challengeId}/show")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Accept a challenge
     * Accept an incoming challenge.
     * You should receive a `gameStart` event on the [incoming events stream](#operation/apiStreamEvent).
     */
    suspend fun challengeAccept(challengeId: String, color: String? = null): Result<Ok> {
        return try {
            val queryParams = mapOf(
                "color" to color
            ).filterValues { it != null }
            val result: Ok = apiClient.safePost("api/challenge/${challengeId}/accept", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Decline a challenge
     * Decline an incoming challenge.
     */
    suspend fun challengeDecline(challengeId: String, reason: String? = null): Result<Ok> {
        return try {
            val formBody = mapOf(
                "reason" to reason
            ).filterValues { it != null }
            val result: Ok = apiClient.safePost("api/challenge/${challengeId}/decline", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Cancel a challenge
     * Cancel a challenge you sent, or aborts the game if the challenge was accepted, but the game was not yet played.
     * Note that the ID of a game is the same as the ID of the challenge that created it.
     */
    suspend fun challengeCancel(challengeId: String, opponentToken: String? = null): Result<Ok> {
        return try {
            val queryParams = mapOf(
                "opponentToken" to opponentToken
            ).filterValues { it != null }
            val result: Ok = apiClient.safePost("api/challenge/${challengeId}/cancel", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Challenge the AI
     * Start a game with Lichess AI.
     * You will be notified on the [event stream](#operation/apiStreamEvent) that a new game has started.
     */
    suspend fun challengeAi(level: Int, clockLimit: Int? = null, clockIncrement: Int? = null, days: Int? = null, color: String? = null, variant: String? = null, fen: String? = null): Result<ChallengeaiResponse> {
        return try {
            val formBody = mapOf(
                "level" to level,
                "clock.limit" to clockLimit,
                "clock.increment" to clockIncrement,
                "days" to days,
                "color" to color,
                "variant" to variant,
                "fen" to fen
            ).filterValues { it != null }
            val result: ChallengeaiResponse = apiClient.safePost("api/challenge/ai", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Open-ended challenge
     * Create a challenge that any 2 players can join.
     * Share the URL of the challenge. the first 2 players to click it will be paired for a game.
     */
    suspend fun challengeOpen(rated: Boolean? = null, clockLimit: Int? = null, clockIncrement: Int? = null, days: Int? = null, variant: String? = null, fen: String? = null, name: String? = null, rules: String? = null, users: String? = null, expiresAt: Int? = null): Result<ChallengeOpenJson> {
        return try {
            val formBody = mapOf(
                "rated" to rated,
                "clock.limit" to clockLimit,
                "clock.increment" to clockIncrement,
                "days" to days,
                "variant" to variant,
                "fen" to fen,
                "name" to name,
                "rules" to rules,
                "users" to users,
                "expiresAt" to expiresAt
            ).filterValues { it != null }
            val result: ChallengeOpenJson = apiClient.safePost("api/challenge/open", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Start clocks of a game
     * Start the clocks of a game immediately, even if a player has not yet made a move.
     * Requires the OAuth tokens of both players with `challenge:write` scope.
     */
    suspend fun challengeStartClocks(gameId: String, token1: String, token2: String? = null): Result<Ok> {
        return try {
            val queryParams = mapOf(
                "token1" to token1,
                "token2" to token2
            ).filterValues { it != null }
            val result: Ok = apiClient.safePost("api/challenge/${gameId}/start-clocks", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Add time to the opponent clock
     * Add seconds to the opponent's clock. Can be used to create games with time odds.
     */
    suspend fun roundAddTime(gameId: String, seconds: Int): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/round/${gameId}/add-time/${seconds}")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Admin challenge tokens
     * **This endpoint can only be used by Lichess administrators. It will not work if you do not have the appropriate permissions.** Tournament organizers should instead use [OAuth](#tag/OAuth) to obtain `challenge:write` tokens from users in order to perform bulk pairing.*
     * Create and obtain `challenge:write` tokens for multiple users.
     */
    suspend fun adminChallengeTokens(users: String, description: String): Result<Map<String, Any>> {
        return try {
            val formBody = mapOf(
                "users" to users,
                "description" to description
            ).filterValues { it != null }
            val result: Map<String, Any> = apiClient.safePost("api/token/admin-challenge", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
