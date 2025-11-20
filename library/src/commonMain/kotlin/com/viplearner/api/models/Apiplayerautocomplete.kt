package com.viplearner.api.models

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

object ApiplayerautocompleteSerializer : JsonContentPolymorphicSerializer<Apiplayerautocomplete>(
    Apiplayerautocomplete::class,
) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Apiplayerautocomplete> {
        val jsonObject = element.jsonObject
        return when {
            "result" in jsonObject -> Apiplayerautocomplete.Variant2.serializer()
            else -> Apiplayerautocomplete.Variant2.serializer()
        }
    }
}

@Serializable(with = ApiplayerautocompleteSerializer::class)
sealed class Apiplayerautocomplete {
    @Serializable
    @SerialName("variant2")
    data class Variant2(
        val result: List<LightUserOnline>? = null,
    ) : Apiplayerautocomplete()
}
