package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for Opening Explorer API endpoints
 * Provides methods to interact with Lichess opening explorer data
 */
class OpeningExplorerService(
    private val apiClient: BaseApiClient,
) {
    /**
     * Masters database
     * **Endpoint: <https://explorer.lichess.ovh/masters>**
     */
    suspend fun openingExplorerMaster(
        fen: String? = null,
        play: String? = null,
        since: Int? = null,
        until: Int? = null,
        moves: Int? = null,
        topGames: Int? = null,
    ): Result<OpeningExplorerMasters> {
        return try {
            val queryParams =
                mapOf(
                    "fen" to fen,
                    "play" to play,
                    "since" to since,
                    "until" to until,
                    "moves" to moves,
                    "topGames" to topGames,
                ).filterValues { it != null }
            val result: OpeningExplorerMasters = apiClient.safeGet("masters", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Lichess games
     * **Endpoint: <https://explorer.lichess.ovh/lichess>**
     */
    suspend fun openingExplorerLichess(
        variant: String? = null,
        fen: String? = null,
        play: String? = null,
        speeds: List<String>? = null,
        ratings: List<Long>? = null,
        since: String? = null,
        until: String? = null,
        moves: Int? = null,
        topGames: Int? = null,
        recentGames: Int? = null,
        history: Boolean? = null,
    ): Result<OpeningExplorerLichess> {
        return try {
            val queryParams =
                mapOf(
                    "variant" to variant,
                    "fen" to fen,
                    "play" to play,
                    "speeds" to speeds,
                    "ratings" to ratings,
                    "since" to since,
                    "until" to until,
                    "moves" to moves,
                    "topGames" to topGames,
                    "recentGames" to recentGames,
                    "history" to history,
                ).filterValues { it != null }
            val result: OpeningExplorerLichess = apiClient.safeGet("lichess", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Player games
     * **Endpoint: <https://explorer.lichess.ovh/player>**
     */
    suspend fun openingExplorerPlayer(
        player: String,
        color: String,
        variant: String? = null,
        fen: String? = null,
        play: String? = null,
        speeds: List<String>? = null,
        modes: List<String>? = null,
        since: String? = null,
        until: String? = null,
        moves: Int? = null,
        recentGames: Int? = null,
    ): Result<Flow<OpeningExplorerPlayer>> {
        return try {
            val queryParams =
                mapOf(
                    "player" to player,
                    "color" to color,
                    "variant" to variant,
                    "fen" to fen,
                    "play" to play,
                    "speeds" to speeds,
                    "modes" to modes,
                    "since" to since,
                    "until" to until,
                    "moves" to moves,
                    "recentGames" to recentGames,
                ).filterValues { it != null }
            val result: Flow<OpeningExplorerPlayer> = apiClient.safeNdjsonGet("player", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * OTB master game
     * **Endpoint: `https://explorer.lichess.ovh/masters/pgn/{gameId}`**
     */
    suspend fun openingExplorerMasterGame(gameId: String): Result<String> {
        return try {
            val result: String = apiClient.safeGet("master/pgn/$gameId")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
