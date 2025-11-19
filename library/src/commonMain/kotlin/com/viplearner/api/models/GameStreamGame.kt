package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * "GameStreamGame": {
 *         "type": "object",
 *         "properties": {
 *           "id": {
 *             "type": "string"
 *           },
 *           "rated": {
 *             "type": "boolean"
 *           },
 *           "variant": {
 *             "$ref": "#/components/schemas/VariantKey"
 *           },
 *           "speed": {
 *             "$ref": "#/components/schemas/Speed"
 *           },
 *           "perf": {
 *             "$ref": "#/components/schemas/PerfType"
 *           },
 *           "createdAt": {
 *             "type": "integer"
 *           },
 *           "status": {
 *             "$ref": "#/components/schemas/GameStatusId"
 *           },
 *           "statusName": {
 *             "$ref": "#/components/schemas/GameStatusName"
 *           },
 *           "clock": {
 *             "type": "object",
 *             "properties": {
 *               "initial": {
 *                 "type": "integer"
 *               },
 *               "increment": {
 *                 "type": "integer"
 *               },
 *               "totalTime": {
 *                 "type": "integer"
 *               }
 *             }
 *           },
 *           "players": {
 *             "type": "object",
 *             "properties": {
 *               "white": {
 *                 "type": "object",
 *                 "properties": {
 *                   "userId": {
 *                     "type": "string"
 *                   },
 *                   "rating": {
 *                     "type": "integer"
 *                   }
 *                 }
 *               },
 *               "black": {
 *                 "type": "object",
 *                 "properties": {
 *                   "userId": {
 *                     "type": "string"
 *                   },
 *                   "rating": {
 *                     "type": "integer"
 *                   }
 *                 }
 *               }
 *             }
 *           },
 *           "winner": {
 *             "$ref": "#/components/schemas/GameColor"
 *           }
 *         },
 *         "required": [
 *           "id"
 *         ],
 */
@Serializable
data class GameStreamGameClock(
    val initial: Int? = null,
    val increment: Int? = null,
    val totalTime: Int? = null
)

@Serializable
data class GameStreamGamePlayers(
    val white: Player? = null,
    val black: Player? = null
) {
    @Serializable
    data class Player(
        val userId: String,
        val rating: Int
    )
}

@Serializable
data class GameStreamGame(
    val id: String,
    val rated: Boolean? = null,
    val variant: VariantKey? = null,
    val speed: Speed? = null,
    val perf: PerfType? = null,
    val createdAt: Int? = null,
    val status: GameStatusId? = null,
    val statusName: GameStatusName? = null,
    val clock: GameStreamGameClock? = null,
    val players: GameStreamGamePlayers? = null,
    val winner: GameColor? = null
) {

}
