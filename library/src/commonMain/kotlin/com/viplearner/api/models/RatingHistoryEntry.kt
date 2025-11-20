package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class RatingHistoryEntry(
    val name: String? = null,
    val points: List<List<Long>>? = null,
)
