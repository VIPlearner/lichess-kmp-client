package com.viplearner.api.models

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

object GameEventOpponentSerializer : JsonContentPolymorphicSerializer<GameEventOpponent>(
    GameEventOpponent::class,
) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<GameEventOpponent> {
        val jsonObject = element.jsonObject

        return when {
            "rating" in jsonObject -> GameEventOpponent.Player.serializer()
            "ai" in jsonObject -> GameEventOpponent.AiOpponent.serializer()
            else -> GameEventOpponent.AiOpponent.serializer()
        }
    }
}

@Serializable(with = GameEventOpponentSerializer::class)
sealed class GameEventOpponent {
    @Serializable
    @SerialName("player")
    data class Player(
        val id: String,
        val username: String,
        val rating: Long,
        val ratingDiff: Long? = null,
    ) : GameEventOpponent()

    @Serializable
    @SerialName("ai_opponent")
    data class AiOpponent(
        val id: String? = null,
        val username: String,
        val ai: Long,
    ) : GameEventOpponent()
}
