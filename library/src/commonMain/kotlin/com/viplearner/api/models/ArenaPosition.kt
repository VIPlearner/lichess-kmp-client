package com.viplearner.api.models

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject


object ArenaPositionSerializer : JsonContentPolymorphicSerializer<ArenaPosition>(
    ArenaPosition::class
) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<ArenaPosition> {
        val jsonObject = element.jsonObject
        return when {
            "eco" in jsonObject && "url" in jsonObject -> ArenaPosition.Thematic.serializer()
            jsonObject["name"]?.toString()?.trim('\"') == "Custom position" -> ArenaPosition.CustomPosition.serializer()
            else -> ArenaPosition.CustomPosition.serializer()
        }
    }
}

@Serializable(with = ArenaPositionSerializer::class)
sealed class ArenaPosition {
    @Serializable
    @SerialName("thematic")
    data class Thematic(
        val eco: String,
        val name: String,
        val fen: String,
        val url: String
    ) : ArenaPosition()

    @Serializable
    @SerialName("Custom position")
    data class CustomPosition(
        val name: String,
        val fen: String
    ) : ArenaPosition()
}
