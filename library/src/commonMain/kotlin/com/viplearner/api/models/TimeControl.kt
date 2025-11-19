package com.viplearner.api.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator


@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class TimeControl {
    @Serializable
    @SerialName("clock")
    data class RealTime(
        val type: String? = null,
        val limit: Int? = null,
        val increment: Int? = null,
        val show: String? = null
    ) : TimeControl()

    @Serializable
    @SerialName("correspondence")
    data class Correspondence(
        val type: String? = null,
        val daysPerTurn: Int? = null
    ) : TimeControl()

    @Serializable
    @SerialName("unlimited")
    data class Unlimited(
        val type: String? = null
    ) : TimeControl()
}
