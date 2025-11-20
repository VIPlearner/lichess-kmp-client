package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TimelineEntryPlanRenewType {
    @SerialName("plan-renew")
    PLAN_RENEW,
}

@Serializable
data class TimelineEntryPlanRenewData(
    val userId: String,
    val months: Double,
)

@Serializable
data class TimelineEntryPlanRenew(
    val type: TimelineEntryPlanRenewType,
    val date: Double,
    val data: TimelineEntryPlanRenewData,
)
