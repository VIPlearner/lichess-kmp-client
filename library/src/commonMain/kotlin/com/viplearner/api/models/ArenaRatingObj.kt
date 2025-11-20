package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ArenaRatingObj(
    val perf: PerfType? = null,
    val rating: Long,
)
