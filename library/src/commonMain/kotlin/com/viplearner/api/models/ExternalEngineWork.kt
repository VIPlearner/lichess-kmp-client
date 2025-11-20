package com.viplearner.api.models

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

object ExternalEngineWorkSerializer : JsonContentPolymorphicSerializer<ExternalEngineWork>(
    ExternalEngineWork::class,
) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<ExternalEngineWork> {
        val jsonObject = element.jsonObject
        return when {
            "movetime" in jsonObject -> ExternalEngineWork.SearchByMovetime.serializer()
            "depth" in jsonObject -> ExternalEngineWork.SearchByDepth.serializer()
            "nodes" in jsonObject -> ExternalEngineWork.SearchByNodes.serializer()
            else -> ExternalEngineWork.SearchByNodes.serializer()
        }
    }
}

@Serializable(with = ExternalEngineWorkSerializer::class)
sealed class ExternalEngineWork {
    @Serializable
    @SerialName("search_by_movetime")
    data class SearchByMovetime(
        val movetime: Long,
        val sessionId: String,
        val threads: Long,
        val hash: Long,
        val multiPv: Long,
        val variant: UciVariant,
        val initialFen: String,
        val moves: List<String>,
    ) : ExternalEngineWork()

    @Serializable
    @SerialName("search_by_depth")
    data class SearchByDepth(
        val depth: Long,
        val sessionId: String,
        val threads: Long,
        val hash: Long,
        val multiPv: Long,
        val variant: UciVariant,
        val initialFen: String,
        val moves: List<String>,
    ) : ExternalEngineWork()

    @Serializable
    @SerialName("search_by_nodes")
    data class SearchByNodes(
        val nodes: Long,
        val sessionId: String,
        val threads: Long,
        val hash: Long,
        val multiPv: Long,
        val variant: UciVariant,
        val initialFen: String,
        val moves: List<String>,
    ) : ExternalEngineWork()
}
