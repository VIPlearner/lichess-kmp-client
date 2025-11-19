package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ArenaPerf(
    val key: PerfType,
    val name: String,
    val position: Int,
    val icon: String? = null
)
