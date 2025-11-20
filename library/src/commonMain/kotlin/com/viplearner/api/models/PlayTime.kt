package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PlayTime(
    val total: Long,
    val tv: Long,
)
