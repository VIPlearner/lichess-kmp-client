package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

object MoveStreamEntrySerializer : JsonContentPolymorphicSerializer<MoveStreamEntry>(
    MoveStreamEntry::class
) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<MoveStreamEntry> {
        val jsonObject = element.jsonObject

        return when {
            "id" in jsonObject && "variant" in jsonObject -> MoveStreamEntry.Variant1.serializer()
            "lm" in jsonObject && "wc" in jsonObject -> MoveStreamEntry.Variant2.serializer()
            else -> MoveStreamEntry.Variant2.serializer()
        }
    }
}

@Serializable(with = MoveStreamEntrySerializer::class)
sealed class MoveStreamEntry {
    @Serializable
    @SerialName("variant1")
    data class Variant1(
        val id: String,
        val variant: Variant? = null,
        val speed: Speed? = null,
        val perf: PerfType? = null,
        val rated: Boolean? = null,
        val initialFen: String? = null,
        val fen: String? = null,
        val player: GameColor? = null,
        val turns: Int? = null,
        val startedAtTurn: Int? = null,
        val source: GameSource? = null,
        val status: GameStatus? = null,
        val createdAt: Int? = null,
        val lastMove: String? = null,
        val players: GamePlayers? = null
    ) : MoveStreamEntry()

    @Serializable
    @SerialName("variant2")
    data class Variant2(
        val fen: String,
        val lm: String? = null,
        val wc: Int,
        val bc: Int
    ) : MoveStreamEntry()

}
