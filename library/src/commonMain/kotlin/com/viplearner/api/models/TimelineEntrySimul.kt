package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class TimelineEntrySimulType {
    @SerialName("simul-create")
    SIMUL_CREATE,

    @SerialName("simul-join")
    SIMUL_JOIN
}

@Serializable
data class TimelineEntrySimulData(
    val userId: String,
    val simulId: String,
    val simulName: String
)

@Serializable
data class TimelineEntrySimul(
    val type: TimelineEntrySimulType,
    val date: Double,
    val data: TimelineEntrySimulData
)
