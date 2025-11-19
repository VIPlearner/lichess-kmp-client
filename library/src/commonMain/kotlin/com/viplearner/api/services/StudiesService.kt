package com.viplearner.api.services

import com.viplearner.api.client.BaseApiClient
import com.viplearner.api.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Service for Studies API endpoints
 * Provides methods to interact with Lichess studies data
 */
class StudiesService(
    private val apiClient: BaseApiClient
) {

    /**
     * Export one study chapter
     * Download one study chapter in PGN format.
     * If authenticated, then all public, unlisted, and private study chapters are read.
     */
    suspend fun studyChapterPgn(studyId: String, chapterId: String, clocks: Boolean? = null, comments: Boolean? = null, variations: Boolean? = null, orientation: Boolean? = null): Result<String> {
        return try {
            val queryParams = mapOf(
                "clocks" to clocks,
                "comments" to comments,
                "variations" to variations,
                "orientation" to orientation
            ).filterValues { it != null }
            val result: String = apiClient.safeGet("api/study/${studyId}/${chapterId}.pgn", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export all chapters
     * Download all chapters of a study in PGN format.
     * If authenticated, then all public, unlisted, and private study chapters are read.
     */
    suspend fun studyAllChaptersPgn(studyId: String, clocks: Boolean? = null, comments: Boolean? = null, variations: Boolean? = null, orientation: Boolean? = null): Result<String> {
        return try {
            val queryParams = mapOf(
                "clocks" to clocks,
                "comments" to comments,
                "variations" to variations,
                "orientation" to orientation
            ).filterValues { it != null }
            val result: String = apiClient.safeGet("api/study/${studyId}.pgn", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Import PGN into a study
     * Imports arbitrary PGN into an existing [study](https://lichess.org/study). Creates a new chapter in the study.
     * If the PGN contains multiple games (separated by 2 or more newlines)
     */
    suspend fun studyImportPGN(studyId: String, pgn: String, name: String? = null, orientation: String? = null, variant: String? = null): Result<StudyImportPgnChapters> {
        return try {
            val formBody = mapOf(
                "pgn" to pgn,
                "name" to name,
                "orientation" to orientation,
                "variant" to variant
            ).filterValues { it != null }
            val result: StudyImportPgnChapters = apiClient.safePost("api/study/${studyId}/import-pgn", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Update PGN tags of a study chapter
     * Add, update and delete the PGN tags of a study.
     * By providing a list of PGN tags in the usual PGN format, you can:
     */
    suspend fun studyChapterTags(studyId: String, chapterId: String, pgn: String): Result<Unit> {
        return try {
            val formBody = mapOf(
                "pgn" to pgn
            ).filterValues { it != null }
            val result: Unit = apiClient.safePost("api/study/${studyId}/${chapterId}/tags", body = formBody)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export all studies of a user
     * Download all chapters of all studies of a user in PGN format.
     * If authenticated, then all public, unlisted, and private studies are included.
     */
    suspend fun studyExportAllPgn(username: String, clocks: Boolean? = null, comments: Boolean? = null, variations: Boolean? = null, orientation: Boolean? = null): Result<String> {
        return try {
            val queryParams = mapOf(
                "clocks" to clocks,
                "comments" to comments,
                "variations" to variations,
                "orientation" to orientation
            ).filterValues { it != null }
            val result: String = apiClient.safeGet("study/by/${username}/export.pgn", queryParams)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * List studies of a user
     * Get metadata (name and dates) of all studies of a user.
     * If authenticated, then all public, unlisted, and private studies are included.
     */
    suspend fun studyListMetadata(username: String): Result<Flow<StudyMetadata>> {
        return try {
            val result: Flow<StudyMetadata> = apiClient.safeGet("api/study/by/${username}")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Delete a study chapter
     * Delete a chapter of a study you own. This is definitive.
     * A study must have at least one chapter; so if you delete the last chapter,
     */
    suspend fun studyStudyIdChapterIdDelete(studyId: String, chapterId: String): Result<Unit> {
        return try {
            val result: Unit = apiClient.safeDelete("api/study/${studyId}/${chapterId}")
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
