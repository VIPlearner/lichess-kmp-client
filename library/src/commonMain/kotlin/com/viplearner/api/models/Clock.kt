package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Clock(
    val limit: Long,
    val increment: Long,
)
