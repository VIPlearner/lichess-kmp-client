package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class TimelineEntryPlanStartType {
    @SerialName("plan-start")
    PLAN_START
}

@Serializable
data class TimelineEntryPlanStartData(
    val userId: String
)

@Serializable
data class TimelineEntryPlanStart(
    val type: TimelineEntryPlanStartType,
    val date: Double,
    val data: TimelineEntryPlanStartData
)
