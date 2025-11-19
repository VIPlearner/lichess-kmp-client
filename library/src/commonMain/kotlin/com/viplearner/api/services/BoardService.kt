package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for Board API endpoints
 * Provides methods to interact with Lichess board data
 */
class BoardService(
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
     * Create a seek
     * Create a public seek, to start a game with a random player.
     */
    suspend fun boardSeek(formData: Map<String, String>): Result<BoardSeekResponse> {
        return try {
            val result: BoardSeekResponse = apiClient.safePost("api/board/seek", body = formData)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Stream Board game state
     * Stream the state of a game being played with the Board API, as [ndjson](#section/Introduction/Streaming-with-ND-JSON).
     */
    suspend fun boardGameStream(gameId: String): Result<Flow<BoardgamestreamEvent>> {
        return try {
            val result: Flow<BoardgamestreamEvent> = apiClient.safeGet("api/board/game/stream/${gameId}")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Make a Board move
     * Make a move in a game being played with the Board API.
     * The move can also contain a draw offer/agreement.
     */
    suspend fun boardGameMove(gameId: String, move: String, offeringDraw: Boolean? = null): Result<Ok> {
        return try {
            val queryParams = mapOf(
                "offeringDraw" to offeringDraw
            ).filterValues { it != null }
            val result: Ok = apiClient.safePost("api/board/game/${gameId}/move/${move}", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Write in the chat
     * Post a message to the player or spectator chat, in a game being played with the Board API.
     */
    suspend fun boardGameChatPost(room: String, text: String): Result<Ok> {
        return try {
            val formBody = mapOf(
                "room" to room,
                "text" to text
            ).filterValues { it != null }
            val result: Ok = apiClient.safePost("api/board/game/{gameId}/chat", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Fetch the game chat
     * Get the messages posted in the game chat
     */
    suspend fun boardGameChatGet(): Result<Flow<GameChat>> {
        return try {
            val result: Flow<GameChat> = apiClient.safeGet("api/board/game/{gameId}/chat")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Abort a game
     * Abort a game being played with the Board API.
     */
    suspend fun boardGameAbort(gameId: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/board/game/${gameId}/abort")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Resign a game
     * Resign a game being played with the Board API.
     */
    suspend fun boardGameResign(gameId: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/board/game/${gameId}/resign")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Handle draw offers
     * Create/accept/decline draw offers.
     * - `yes`: Offer a draw, or accept the opponent's draw offer.
     */
    suspend fun boardGameDraw(gameId: String, accept: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/board/game/${gameId}/draw/${accept}")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Handle takeback offers
     * Create/accept/decline takebacks.
     * - `yes`: Propose a takeback, or accept the opponent's takeback offer.
     */
    suspend fun boardGameTakeback(gameId: String, accept: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/board/game/${gameId}/takeback/${accept}")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Claim victory of a game
     * Claim victory when the opponent has left the game for a while.
     */
    suspend fun boardGameClaimVictory(gameId: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/board/game/${gameId}/claim-victory")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Claim draw of a game
     * Claim draw when the opponent has left the game for a while.
     */
    suspend fun boardGameClaimDraw(gameId: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/board/game/${gameId}/claim-draw")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Berserk a tournament game
     * Go berserk on an arena tournament game. Halves the clock time, grants an extra point upon winning.
     * Only available in arena tournaments that allow berserk, and before each player has made a move.
     */
    suspend fun boardGameBerserk(gameId: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/board/game/${gameId}/berserk")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
