package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * "PuzzleAndGame": {
 *         "type": "object",
 *         "properties": {
 *           "game": {
 *             "type": "object",
 *             "properties": {
 *               "clock": {
 *                 "type": "string"
 *               },
 *               "id": {
 *                 "type": "string"
 *               },
 *               "perf": {
 *                 "type": "object",
 *                 "properties": {
 *                   "key": {
 *                     "$ref": "#/components/schemas/PerfType"
 *                   },
 *                   "name": {
 *                     "type": "string"
 *                   }
 *                 },
 *                 "required": [
 *                   "key",
 *                   "name"
 *                 ]
 *               },
 *               "pgn": {
 *                 "type": "string"
 *               },
 *               "players": {
 *                 "type": "array",
 *                 "items": {
 *                   "type": "object",
 *                   "properties": {
 *                     "color": {
 *                       "$ref": "#/components/schemas/GameColor"
 *                     },
 *                     "flair": {
 *                       "$ref": "#/components/schemas/Flair"
 *                     },
 *                     "id": {
 *                       "type": "string"
 *                     },
 *                     "name": {
 *                       "type": "string"
 *                     },
 *                     "patron": {
 *                       "type": "boolean",
 *                       "deprecated": true
 *                     },
 *                     "patronColor": {
 *                       "$ref": "#/components/schemas/PatronColor"
 *                     },
 *                     "rating": {
 *                       "type": "integer"
 *                     },
 *                     "title": {
 *                       "$ref": "#/components/schemas/Title"
 *                     }
 *                   },
 *                   "required": [
 *                     "color",
 *                     "id",
 *                     "name",
 *                     "rating"
 *                   ]
 *                 },
 *                 "minItems": 2,
 *                 "maxItems": 2
 *               },
 *               "rated": {
 *                 "type": "boolean"
 *               }
 *             },
 *             "required": [
 *               "clock",
 *               "id",
 *               "perf",
 *               "pgn",
 *               "players",
 *               "rated"
 *             ],
 *             "additionalProperties": false
 *           },
 *           "puzzle": {
 *             "type": "object",
 *             "properties": {
 *               "id": {
 *                 "type": "string"
 *               },
 *               "initialPly": {
 *                 "type": "integer"
 *               },
 *               "plays": {
 *                 "type": "integer"
 *               },
 *               "rating": {
 *                 "type": "integer"
 *               },
 *               "solution": {
 *                 "type": "array",
 *                 "items": {
 *                   "type": "string"
 *                 }
 *               },
 *               "themes": {
 *                 "type": "array",
 *                 "items": {
 *                   "type": "string"
 *                 }
 *               }
 *             },
 *             "required": [
 *               "id",
 *               "initialPly",
 *               "plays",
 *               "rating",
 *               "solution",
 *               "themes"
 *             ],
 *             "additionalProperties": false
 *           }
 *         },
 *         "required": [
 *           "game",
 *           "puzzle"
 *         ]
 *       },
 */
@Serializable
data class PuzzleAndGameGame(
    val clock: String,
    val id: String,
    val perf: Perf,
    val pgn: String,
    val players: List<Player>,
    val rated: Boolean
) {
    @Serializable
    data class Perf(
        val key: String,
        val name: String
    )

    @Serializable
    data class Player(
        val color: String,
        val flair: Flair,
        val id: String,
        val name: String,
        @Deprecated("patron is deprecated")
        val patron: Boolean?,
        val patronColor: PatronColor,
        val rating: Int,
        val title: Title?
    )
}

@Serializable
data class PuzzleAndGamePuzzle(
    val id: String,
    val initialPly: Int,
    val plays: Int,
    val rating: Int,
    val solution: List<String>,
    val themes: List<String>
)

@Serializable
data class PuzzleAndGame(
    val game: PuzzleAndGameGame,
    val puzzle: PuzzleAndGamePuzzle
)
