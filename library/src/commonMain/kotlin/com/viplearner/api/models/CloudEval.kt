package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.jsonObject

@Serializable
data class CloudEval(
    val depth: Int,
    val fen: String,
    val knodes: Int,
    val pvs: List<Pv>
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
            val cp: Int,
            val moves: String
        ) : Pv()

        @Serializable
        @SerialName("Mate variation")
        data class MateVariation(
            val mate: Int,
            val moves: String
        ) : Pv()
    }
}
