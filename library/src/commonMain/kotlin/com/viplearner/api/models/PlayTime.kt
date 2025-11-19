package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PlayTime(
    val total: Int,
    val tv: Int
)
