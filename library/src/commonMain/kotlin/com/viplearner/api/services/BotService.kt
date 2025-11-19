package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for Bot API endpoints
 * Provides methods to interact with Lichess bot data
 */
class BotService(
    private val apiClient: BaseApiClient
) {

    /**
     * Stream incoming events
     * Stream the events reaching a lichess user in real time as [ndjson](#section/Introduction/Streaming-with-ND-JSON).
     */
    suspend fun streamEvent(): Result<Flow<ApiStreamEvent>> {
        return try {
            val result: Flow<ApiStreamEvent> = apiClient.safeGet("api/stream/event")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get online bots
     * Stream the [online bot users](https://lichess.org/player/bots), as [ndjson](#section/Introduction/Streaming-with-ND-JSON). Throttled to 50 bot users per second.
     */
    suspend fun botOnline(nb: Int? = null): Result<Flow<User>> {
        return try {
            val queryParams = mapOf(
                "nb" to nb
            ).filterValues { it != null }
            val result: Flow<User> = apiClient.safeGet("api/bot/online", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Upgrade to Bot account
     * Upgrade a lichess player account into a Bot account. Only Bot accounts can use the Bot API.
     * The account **cannot have played any game** before becoming a Bot account. The upgrade is **irreversible**. The account will only be able to play as a Bot.
     */
    suspend fun botAccountUpgrade(): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/bot/account/upgrade")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Stream Bot game state
     * Stream the state of a game being played with the Bot API, as [ndjson](#section/Introduction/Streaming-with-ND-JSON).
     * Use this endpoint to get updates about the game in real-time, with a single request.
     */
    suspend fun botGameStream(gameId: String): Result<Flow<BotgamestreamEvent>> {
        return try {
            val result: Flow<BotgamestreamEvent> = apiClient.safeGet("api/bot/game/stream/${gameId}")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Make a Bot move
     * Make a move in a game being played with the Bot API.
     * The move can also contain a draw offer/agreement.
     */
    suspend fun botGameMove(gameId: String, move: String, offeringDraw: Boolean? = null): Result<Ok> {
        return try {
            val queryParams = mapOf(
                "offeringDraw" to offeringDraw
            ).filterValues { it != null }
            val result: Ok = apiClient.safePost("api/bot/game/${gameId}/move/${move}", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Write in the chat
     * Post a message to the player or spectator chat, in a game being played with the Bot API.
     */
    suspend fun botGameChat(gameId: String, room: String, text: String): Result<Ok> {
        return try {
            val formBody = mapOf(
                "room" to room,
                "text" to text
            ).filterValues { it != null }
            val result: Ok = apiClient.safePost("api/bot/game/${gameId}/chat", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Fetch the game chat
     * Get the messages posted in the game chat
     */
    suspend fun botGameChatGet(gameId: String): Result<Flow<GameChat>> {
        return try {
            val result: Flow<GameChat> = apiClient.safeGet("api/bot/game/${gameId}/chat")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Abort a game
     * Abort a game being played with the Bot API.
     */
    suspend fun botGameAbort(gameId: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/bot/game/${gameId}/abort")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Resign a game
     * Resign a game being played with the Bot API.
     */
    suspend fun botGameResign(gameId: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/bot/game/${gameId}/resign")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Handle draw offers
     * Create/accept/decline draw offers with the Bot API.
     * - `yes`: Offer a draw, or accept the opponent's draw offer.
     */
    suspend fun botGameDraw(gameId: String, accept: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/bot/game/${gameId}/draw/${accept}")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Handle takeback offers
     * Create/accept/decline takebacks with the Bot API.
     * - `yes`: Propose a takeback, or accept the opponent's takeback offer.
     */
    suspend fun botGameTakeback(gameId: String, accept: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/bot/game/${gameId}/takeback/${accept}")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Claim victory of a game
     * Claim victory when the opponent has left the game for a while.
     */
    suspend fun botGameClaimVictory(gameId: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/bot/game/${gameId}/claim-victory")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Claim draw of a game
     * Claim draw when the opponent has left the game for a while.
     */
    suspend fun botGameClaimDraw(gameId: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/bot/game/${gameId}/claim-draw")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
