package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.PuzzleActivity
import com.viplearner.api.models.PuzzleAndGame
import com.viplearner.api.models.PuzzleBatchSelect
import com.viplearner.api.models.PuzzleBatchSolveRequest
import com.viplearner.api.models.PuzzleBatchSolveResponse
import com.viplearner.api.models.PuzzleDashboard
import com.viplearner.api.models.PuzzleRaceResults
import com.viplearner.api.models.PuzzleRacer
import com.viplearner.api.models.PuzzleReplay
import com.viplearner.api.models.PuzzleStormDashboard
import kotlinx.coroutines.flow.Flow

/**
 * Service for Puzzles API endpoints
 * Provides methods to interact with Lichess puzzles data
 */
class PuzzlesService(
    private val apiClient: BaseApiClient,
) {
    /**
     * Get the daily puzzle
     * Get the daily Lichess puzzle in JSON format.
     * Alternatively, you can [post it in your slack workspace](https://lichess.org/daily-puzzle-slack).
     */
    suspend fun puzzleDaily(): Result<PuzzleAndGame> {
        return try {
            val result: PuzzleAndGame = apiClient.safeGet("api/puzzle/daily")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get a puzzle by its ID
     * Get a single Lichess puzzle in JSON format.
     */
    suspend fun puzzleId(id: String): Result<PuzzleAndGame> {
        return try {
            val result: PuzzleAndGame = apiClient.safeGet("api/puzzle/$id")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get a new puzzle
     * Get a random Lichess puzzle in JSON format.
     */
    suspend fun puzzleNext(
        angle: String? = null,
        difficulty: String? = null,
        color: String? = null,
    ): Result<PuzzleAndGame> {
        return try {
            val queryParams =
                mapOf(
                    "angle" to angle,
                    "difficulty" to difficulty,
                    "color" to color,
                ).filterValues { it != null }
            val result: PuzzleAndGame = apiClient.safeGet("api/puzzle/next", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get multiple puzzles at once
     * Get a batch of random Lichess puzzles in JSON format.
     */
    suspend fun puzzleBatchSelect(
        angle: String,
        difficulty: String? = null,
        nb: Int? = null,
        color: String? = null,
    ): Result<PuzzleBatchSelect> {
        return try {
            val queryParams =
                mapOf(
                    "difficulty" to difficulty,
                    "nb" to nb,
                    "color" to color,
                ).filterValues { it != null }
            val result: PuzzleBatchSelect = apiClient.safeGet("api/puzzle/batch/$angle", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Solve multiple puzzles at once
     * Set puzzles as solved and update ratings.
     */
    suspend fun puzzleBatchSolve(
        angle: String,
        nb: Int? = null,
        body: PuzzleBatchSolveRequest,
    ): Result<PuzzleBatchSolveResponse> {
        return try {
            val queryParams =
                mapOf(
                    "nb" to nb,
                ).filterValues { it != null }
            val result: PuzzleBatchSolveResponse = apiClient.safePost("api/puzzle/batch/$angle", queryParams, body)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get your puzzle activity
     * Download your puzzle activity in [ndjson](#section/Introduction/Streaming-with-ND-JSON) format.
     * Puzzle activity is sorted by reverse chronological order (most recent first)
     */
    suspend fun puzzleActivity(
        max: Int? = null,
        before: Int? = null,
    ): Result<Flow<PuzzleActivity>> {
        return try {
            val queryParams =
                mapOf(
                    "max" to max,
                    "before" to before,
                ).filterValues { it != null }
            val result: Flow<PuzzleActivity> = apiClient.safeNdjsonGet("api/puzzle/activity", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get puzzles to replay
     * Gets the puzzle IDs of remaining puzzles to re-attempt in JSON format.
     */
    suspend fun puzzleReplay(
        days: Int,
        theme: String,
    ): Result<PuzzleReplay> {
        return try {
            val result: PuzzleReplay = apiClient.safeGet("api/puzzle/replay/$days/$theme")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get your puzzle dashboard
     * Download your [puzzle dashboard](https://lichess.org/training/dashboard/30/dashboard) as JSON.
     * Also includes all puzzle themes played, with aggregated results.
     */
    suspend fun puzzleDashboard(days: Int): Result<PuzzleDashboard> {
        return try {
            val result: PuzzleDashboard = apiClient.safeGet("api/puzzle/dashboard/$days")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get the storm dashboard of a player
     * Download the [storm dashboard](https://lichess.org/storm/dashboard/mrbasso) of any player as JSON.
     * Contains the aggregated highscores, and the history of storm runs aggregated by days.
     */
    suspend fun stormDashboard(
        username: String,
        days: Int? = null,
    ): Result<PuzzleStormDashboard> {
        return try {
            val queryParams =
                mapOf(
                    "days" to days,
                ).filterValues { it != null }
            val result: PuzzleStormDashboard = apiClient.safeGet("api/storm/dashboard/$username", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Create and join a puzzle race
     * Create a new private [puzzle race](https://lichess.org/racer).
     * The Lichess user who creates the race must join the race page,
     */
    suspend fun racerPost(): Result<PuzzleRacer> {
        return try {
            val result: PuzzleRacer = apiClient.safePost("api/racer")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get puzzle race results
     * Get the results of a [puzzle race](https://lichess.org/racer).
     * Returns information about players, puzzles, and the current status of the race.
     */
    suspend fun racerGet(id: String): Result<PuzzleRaceResults> {
        return try {
            val result: PuzzleRaceResults = apiClient.safeGet("api/racer/$id")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
