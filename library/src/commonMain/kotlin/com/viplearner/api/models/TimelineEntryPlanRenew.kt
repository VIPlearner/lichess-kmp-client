package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class TimelineEntryPlanRenewType {
    @SerialName("plan-renew")
    PLAN_RENEW
}

@Serializable
data class TimelineEntryPlanRenewData(
    val userId: String,
    val months: Double
)

@Serializable
data class TimelineEntryPlanRenew(
    val type: TimelineEntryPlanRenewType,
    val date: Double,
    val data: TimelineEntryPlanRenewData
)
