package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ArenaPerf(
    val key: PerfType,
    val name: String,
    val position: Long,
    val icon: String? = null,
)
