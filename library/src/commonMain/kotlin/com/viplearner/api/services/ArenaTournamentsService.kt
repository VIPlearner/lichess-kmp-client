package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for Arena tournaments API endpoints
 * Provides methods to interact with Lichess arena tournaments data
 */
class ArenaTournamentsService(
    private val apiClient: BaseApiClient
) {

    /**
     * Get current tournaments
     * Get recently active and finished tournaments.
     * This API is used to display the [Lichess tournament schedule](https://lichess.org/tournament).
     */
    suspend fun tournament(): Result<ArenaTournaments> {
        return try {
            val result: ArenaTournaments = apiClient.safeGet("api/tournament")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Create a new Arena tournament
     * Create a public or private Arena tournament.
     * This endpoint mirrors the form on <https://lichess.org/tournament/new>.
     */
    suspend fun tournamentPost(name: String? = null, clockTime: Double, clockIncrement: Int, minutes: Int, waitMinutes: Int? = null, startDate: Int? = null, variant: String? = null, rated: Boolean? = null, position: String? = null, berserkable: Boolean? = null, streakable: Boolean? = null, hasChat: Boolean? = null, description: String? = null, password: String? = null, teamBattleByTeam: String? = null, conditionsTeammemberTeamid: String? = null, conditionsMinratingRating: Int? = null, conditionsMaxratingRating: Int? = null, conditionsNbratedgameNb: Int? = null, conditionsAllowlist: String? = null, conditionsBots: Boolean? = null, conditionsAccountage: Int? = null): Result<ArenaTournamentFull> {
        return try {
            val formBody = mapOf(
                "name" to name,
                "clockTime" to clockTime,
                "clockIncrement" to clockIncrement,
                "minutes" to minutes,
                "waitMinutes" to waitMinutes,
                "startDate" to startDate,
                "variant" to variant,
                "rated" to rated,
                "position" to position,
                "berserkable" to berserkable,
                "streakable" to streakable,
                "hasChat" to hasChat,
                "description" to description,
                "password" to password,
                "teamBattleByTeam" to teamBattleByTeam,
                "conditions.teamMember.teamId" to conditionsTeammemberTeamid,
                "conditions.minRating.rating" to conditionsMinratingRating,
                "conditions.maxRating.rating" to conditionsMaxratingRating,
                "conditions.nbRatedGame.nb" to conditionsNbratedgameNb,
                "conditions.allowList" to conditionsAllowlist,
                "conditions.bots" to conditionsBots,
                "conditions.accountAge" to conditionsAccountage
            ).filterValues { it != null }
            val result: ArenaTournamentFull = apiClient.safePost("api/tournament", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get info about an Arena tournament
     * Get detailed info about recently finished, current, or upcoming tournament's duels, player standings, and other info.
     */
    suspend fun tournament(page: Int? = null): Result<ArenaTournamentFull> {
        return try {
            val queryParams = mapOf(
                "page" to page
            ).filterValues { it != null }
            val result: ArenaTournamentFull = apiClient.safeGet("api/tournament/{id}", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Update an Arena tournament
     * Update an Arena tournament.
     * Be mindful not to make important changes to ongoing tournaments.
     */
    suspend fun tournamentUpdate(name: String? = null, clockTime: Double, clockIncrement: Int, minutes: Int, waitMinutes: Int? = null, startDate: Int? = null, variant: String? = null, rated: Boolean? = null, position: String? = null, berserkable: Boolean? = null, streakable: Boolean? = null, hasChat: Boolean? = null, description: String? = null, password: String? = null, conditionsMinratingRating: Int? = null, conditionsMaxratingRating: Int? = null, conditionsNbratedgameNb: Int? = null, conditionsAllowlist: String? = null, conditionsBots: Boolean? = null, conditionsAccountage: Int? = null): Result<ArenaTournamentFull> {
        return try {
            val formBody = mapOf(
                "name" to name,
                "clockTime" to clockTime,
                "clockIncrement" to clockIncrement,
                "minutes" to minutes,
                "waitMinutes" to waitMinutes,
                "startDate" to startDate,
                "variant" to variant,
                "rated" to rated,
                "position" to position,
                "berserkable" to berserkable,
                "streakable" to streakable,
                "hasChat" to hasChat,
                "description" to description,
                "password" to password,
                "conditions.minRating.rating" to conditionsMinratingRating,
                "conditions.maxRating.rating" to conditionsMaxratingRating,
                "conditions.nbRatedGame.nb" to conditionsNbratedgameNb,
                "conditions.allowList" to conditionsAllowlist,
                "conditions.bots" to conditionsBots,
                "conditions.accountAge" to conditionsAccountage
            ).filterValues { it != null }
            val result: ArenaTournamentFull = apiClient.safePost("api/tournament/{id}", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Join an Arena tournament
     * Join an Arena tournament, possibly with a password and/or a team.
     * Also unpauses if you had previously [paused](#operation/apiTournamentWithdraw) the tournament.
     */
    suspend fun tournamentJoin(id: String, password: String? = null, team: String? = null, pairMeAsap: Boolean? = null): Result<Ok> {
        return try {
            val formBody = mapOf(
                "password" to password,
                "team" to team,
                "pairMeAsap" to pairMeAsap
            ).filterValues { it != null }
            val result: Ok = apiClient.safePost("api/tournament/${id}/join", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Pause or leave an Arena tournament
     * Leave a future Arena tournament, or take a break on an ongoing Arena tournament.
     * It's possible to join again later. Points and streaks are preserved.
     */
    suspend fun tournamentWithdraw(id: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/tournament/${id}/withdraw")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Terminate an Arena tournament
     * Terminate an Arena tournament
     */
    suspend fun tournamentTerminate(id: String): Result<Ok> {
        return try {
            val result: Ok = apiClient.safePost("api/tournament/${id}/terminate")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Update a team battle
     * Set the teams and number of leaders of a team battle.
     * To update the other attributes of a team battle, use the [tournament update endpoint](#operation/apiTournamentUpdate).
     */
    suspend fun tournamentTeamBattlePost(id: String, teams: String, nbLeaders: Int): Result<ArenaTournamentFull> {
        return try {
            val formBody = mapOf(
                "teams" to teams,
                "nbLeaders" to nbLeaders
            ).filterValues { it != null }
            val result: ArenaTournamentFull = apiClient.safePost("api/tournament/team-battle/${id}", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export games of an Arena tournament
     * Download games of a tournament in PGN or [ndjson](#section/Introduction/Streaming-with-ND-JSON) format.
     * Games are sorted by reverse chronological order (most recent first).
     */
    suspend fun gamesByTournament(id: String, player: String? = null, moves: Boolean? = null, pgnInJson: Boolean? = null, tags: Boolean? = null, clocks: Boolean? = null, evals: Boolean? = null, accuracy: Boolean? = null, opening: Boolean? = null, division: Boolean? = null): Result<Flow<GameJson>> {
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
            val result: Flow<GameJson> = apiClient.safeGet("api/tournament/${id}/games", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get results of an Arena tournament
     * Players of an Arena tournament, with their score and performance, sorted by rank (best first).
     * **Players are streamed as [ndjson](#section/Introduction/Streaming-with-ND-JSON)**, i.e. one JSON object per line.
     */
    suspend fun resultsByTournament(id: String, nb: Int? = null, sheet: Boolean? = null): Result<Flow<ResultsbytournamentResponse>> {
        return try {
            val queryParams = mapOf(
                "nb" to nb,
                "sheet" to sheet
            ).filterValues { it != null }
            val result: Flow<ResultsbytournamentResponse> = apiClient.safeGet("api/tournament/${id}/results", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get team standing of a team battle
     * Teams of a team battle tournament, with top players, sorted by rank (best first).
     */
    suspend fun teamsByTournament(id: String): Result<TournamentTeamsResponse> {
        return try {
            val result: TournamentTeamsResponse = apiClient.safeGet("api/tournament/${id}/teams")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get tournaments created by a user
     * Get all tournaments created by a given user.
     * Tournaments are sorted by reverse chronological order of start date (last starting first).
     */
    suspend fun userNameTournamentCreated(username: String, nb: Int? = null, status: Int? = null): Result<Flow<ArenaTournament>> {
        return try {
            val queryParams = mapOf(
                "nb" to nb,
                "status" to status
            ).filterValues { it != null }
            val result: Flow<ArenaTournament> = apiClient.safeGet("api/user/${username}/tournament/created", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get tournaments played by a user
     * Get all tournaments played by a given user.
     * Tournaments are sorted by reverse chronological order of start date (last played first).
     */
    suspend fun userNameTournamentPlayed(username: String, nb: Int? = null, performance: Boolean? = null): Result<Flow<ArenaTournamentPlayed>> {
        return try {
            val queryParams = mapOf(
                "nb" to nb,
                "performance" to performance
            ).filterValues { it != null }
            val result: Flow<ArenaTournamentPlayed> = apiClient.safeGet("api/user/${username}/tournament/played", queryParams)
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
    suspend fun teamArena(teamId: String, max: Int? = null, status: String? = null, createdBy: String? = null, name: String? = null): Result<Flow<ArenaTournament>> {
        return try {
            val queryParams = mapOf(
                "max" to max,
                "status" to status,
                "createdBy" to createdBy,
                "name" to name
            ).filterValues { it != null }
            val result: Flow<ArenaTournament> = apiClient.safeGet("api/team/${teamId}/arena", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
