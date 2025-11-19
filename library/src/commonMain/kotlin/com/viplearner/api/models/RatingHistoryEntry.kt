package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class RatingHistoryEntry(
    val name: String? = null,
    val points: List<List<Int>>? = null
)
