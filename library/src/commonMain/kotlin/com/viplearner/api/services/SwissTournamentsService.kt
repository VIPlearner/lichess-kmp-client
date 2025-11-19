package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for Swiss tournaments API endpoints
 * Provides methods to interact with Lichess swiss tournaments data
 */
class SwissTournamentsService(
    private val apiClient: BaseApiClient
) {

    /**
     * Create a new Swiss tournament
     * Create a Swiss tournament for your team.
     * This endpoint mirrors the Swiss tournament form from your team pagee.
     */
    suspend fun swissNew(teamId: String, name: String? = null, clockLimit: Int, clockIncrement: Int, nbRounds: Int, startsAt: Int? = null, roundInterval: Int? = null, variant: String? = null, position: String? = null, description: String? = null, rated: Boolean? = null, password: String? = null, forbiddenPairings: String? = null, manualPairings: String? = null, chatFor: Int? = null, conditionsMinratingRating: Int? = null, conditionsMaxratingRating: Int? = null, conditionsNbratedgameNb: Int? = null, conditionsPlayyourgames: Boolean? = null, conditionsAllowlist: String? = null): Result<SwissTournament> {
        return try {
            val formBody = mapOf(
                "name" to name,
                "clock.limit" to clockLimit,
                "clock.increment" to clockIncrement,
                "nbRounds" to nbRounds,
                "startsAt" to startsAt,
                "roundInterval" to roundInterval,
                "variant" to variant,
                "position" to position,
                "description" to description,
                "rated" to rated,
                "password" to password,
                "forbiddenPairings" to forbiddenPairings,
                "manualPairings" to manualPairings,
                "chatFor" to chatFor,
                "conditions.minRating.rating" to conditionsMinratingRating,
                "conditions.maxRating.rating" to conditionsMaxratingRating,
                "conditions.nbRatedGame.nb" to conditionsNbratedgameNb,
                "conditions.playYourGames" to conditionsPlayyourgames,
                "conditions.allowList" to conditionsAllowlist
            ).filterValues { it != null }
            val result: SwissTournament = apiClient.safePost("api/swiss/new/${teamId}", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get info about a Swiss tournament
     * Get detailed info about a Swiss tournament.
     */
    suspend fun swiss(): Result<SwissTournament> {
        return try {
            val result: SwissTournament = apiClient.safeGet("api/swiss/{id}")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Update a Swiss tournament
     * Update a Swiss tournament.
     * Be mindful not to make important changes to ongoing tournaments.
     */
    suspend fun swissUpdate(id: String, name: String? = null, clockLimit: Int, clockIncrement: Int, nbRounds: Int, startsAt: Int? = null, roundInterval: Int? = null, variant: String? = null, position: String? = null, description: String? = null, rated: Boolean? = null, password: String? = null, forbiddenPairings: String? = null, manualPairings: String? = null, chatFor: Int? = null, conditionsMinratingRating: Int? = null, conditionsMaxratingRating: Int? = null, conditionsNbratedgameNb: Int? = null, conditionsPlayyourgames: Boolean? = null, conditionsAllowlist: String? = null): Result<SwissTournament> {
        return try {
            val formBody = mapOf(
                "name" to name,
                "clock.limit" to clockLimit,
                "clock.increment" to clockIncrement,
                "nbRounds" to nbRounds,
                "startsAt" to startsAt,
                "roundInterval" to roundInterval,
                "variant" to variant,
                "position" to position,
                "description" to description,
                "rated" to rated,
                "password" to password,
                "forbiddenPairings" to forbiddenPairings,
                "manualPairings" to manualPairings,
                "chatFor" to chatFor,
                "conditions.minRating.rating" to conditionsMinratingRating,
                "conditions.maxRating.rating" to conditionsMaxratingRating,
                "conditions.nbRatedGame.nb" to conditionsNbratedgameNb,
                "conditions.playYourGames" to conditionsPlayyourgames,
                "conditions.allowList" to conditionsAllowlist
            ).filterValues { it != null }
            val result: SwissTournament = apiClient.safePost("api/swiss/${id}/edit", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Manually schedule the next round
     * Manually schedule the next round date and time of a Swiss tournament.
     * This sets the `roundInterval` field to `99999999`, i.e. manual scheduling.
     */
    suspend fun swissScheduleNextRound(id: String, date: Int? = null): Result<Unit> {
        return try {
            val formBody = mapOf(
                "date" to date
            ).filterValues { it != null }
            val result: Unit = apiClient.safePost("api/swiss/${id}/schedule-next-round", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Join a Swiss tournament
     * Join a Swiss tournament, possibly with a password.
     */
    suspend fun swissJoin(id: String, password: String? = null): Result<Ok> {
        return try {
            val formBody = mapOf(
                "password" to password
            ).filterValues { it != null }
            val result: Ok = apiClient.safePost("api/swiss/${id}/join", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Pause or leave a swiss tournament
     * Leave a future Swiss tournament, or take a break on an ongoing Swiss tournament.
     * It's possible to join again later. Points are preserved.
     */
    suspend fun swissWithdraw(id: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/swiss/${id}/withdraw")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Terminate a Swiss tournament
     * Terminate a Swiss tournament
     */
    suspend fun swissTerminate(id: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/swiss/${id}/terminate")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export TRF of a Swiss tournament
     * Download a tournament in the Tournament Report File format, the FIDE standard.
     * Documentation: <https://www.fide.com/FIDE/handbook/C04Annex2_TRF16.pdf>
     */
    suspend fun swissTrf(id: String): Result<String> {
        return try {
            val result: String = apiClient.safeGet("swiss/${id}.trf")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export games of a Swiss tournament
     * Download games of a swiss tournament in PGN or [ndjson](#section/Introduction/Streaming-with-ND-JSON) format.
     * Games are sorted by chronological order.
     */
    suspend fun gamesBySwiss(id: String, player: String? = null, moves: Boolean? = null, pgnInJson: Boolean? = null, tags: Boolean? = null, clocks: Boolean? = null, evals: Boolean? = null, accuracy: Boolean? = null, opening: Boolean? = null, division: Boolean? = null): Result<Flow<GameJson>> {
        return try {
            val queryParams = mapOf(
                "player" to player,
                "moves" to moves,
                "pgnInJson" to pgnInJson,
                "tags" to tags,
                "clocks" to clocks,
                "evals" to evals,
                "accuracy" to accuracy,
                "opening" to opening,
                "division" to division
            ).filterValues { it != null }
            val result: Flow<GameJson> = apiClient.safeGet("api/swiss/${id}/games", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get results of a swiss tournament
     * Players of a swiss tournament, with their score and performance, sorted by rank (best first).
     * Players are streamed as [ndjson](#section/Introduction/Streaming-with-ND-JSON).
     */
    suspend fun resultsBySwiss(id: String, nb: Int? = null): Result<Flow<ResultsbyswissResponse>> {
        return try {
            val queryParams = mapOf(
                "nb" to nb
            ).filterValues { it != null }
            val result: Flow<ResultsbyswissResponse> = apiClient.safeGet("api/swiss/${id}/results", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get team swiss tournaments
     * Get all swiss tournaments of a team.
     * Tournaments are sorted by reverse chronological order of start date (last starting first).
     */
    suspend fun teamSwiss(teamId: String, max: Int? = null, status: String? = null, createdBy: String? = null, name: String? = null): Result<Flow<SwissTournament>> {
        return try {
            val queryParams = mapOf(
                "max" to max,
                "status" to status,
                "createdBy" to createdBy,
                "name" to name
            ).filterValues { it != null }
            val result: Flow<SwissTournament> = apiClient.safeGet("api/team/${teamId}/swiss", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
