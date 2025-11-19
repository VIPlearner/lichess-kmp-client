package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for TV API endpoints
 * Provides methods to interact with Lichess tv data
 */
class TvService(
    private val apiClient: BaseApiClient
) {

    /**
     * Get current TV games
     * Get basic info about the best games being played for each speed and variant,
     * but also computer games and bot games.
     */
    suspend fun tvChannels(): Result<Unit> {
        return try {
            val result: Unit = apiClient.safeGet("api/tv/channels")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Stream current TV game
     * Stream positions and moves of the current [TV game](https://lichess.org/tv) in [ndjson](#section/Introduction/Streaming-with-ND-JSON).
     * Try it with `curl https://lichess.org/api/tv/feed`.
     */
    suspend fun tvFeed(): Result<Flow<TvFeed>> {
        return try {
            val result: Flow<TvFeed> = apiClient.safeGet("api/tv/feed")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Stream current TV game of a TV channel
     * Stream positions and moves of a current [TV channel's game](https://lichess.org/tv/rapid) in [ndjson](#section/Introduction/Streaming-with-ND-JSON).
     * Try it with `curl https://lichess.org/api/tv/rapid/feed`.
     */
    suspend fun tvChannelFeed(channel: String): Result<Flow<TvFeed>> {
        return try {
            val result: Flow<TvFeed> = apiClient.safeGet("api/tv/${channel}/feed")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get best ongoing games of a TV channel
     * Get a list of ongoing games for a given TV channel. Similar to [lichess.org/games](https://lichess.org/games).
     * Available in PGN or [ndjson](#section/Introduction/Streaming-with-ND-JSON) format, depending on the request `Accept` header.
     */
    suspend fun tvChannelGames(channel: String, nb: Int? = null, moves: Boolean? = null, pgnInJson: Boolean? = null, tags: Boolean? = null, clocks: Boolean? = null, opening: Boolean? = null): Result<Flow<GameJson>> {
        return try {
            val queryParams = mapOf(
                "nb" to nb,
                "moves" to moves,
                "pgnInJson" to pgnInJson,
                "tags" to tags,
                "clocks" to clocks,
                "opening" to opening
            ).filterValues { it != null }
            val result: Flow<GameJson> = apiClient.safeGet("api/tv/${channel}", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
