package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.jsonObject

@Serializable
data class CloudEval(
    val depth: Long,
    val fen: String,
    val knodes: Long,
    val pvs: List<Pv>,
) {
    class PvSerializer : JsonContentPolymorphicSerializer<Pv>(Pv::class) {
        override fun selectDeserializer(element: kotlinx.serialization.json.JsonElement) =
            if (element.jsonObject.containsKey("cp")) {
                Pv.NonMateVariation.serializer()
            } else {
                Pv.MateVariation.serializer()
            }
    }

    @Serializable(with = PvSerializer::class)
    sealed class Pv {
        @Serializable
        @SerialName("Non-mate variation")
        data class NonMateVariation(
            val cp: Long,
            val moves: String,
        ) : Pv()

        @Serializable
        @SerialName("Mate variation")
        data class MateVariation(
            val mate: Long,
            val moves: String,
        ) : Pv()
    }
}
