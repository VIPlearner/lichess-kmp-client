package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class BroadcastPlayerEntry(
    val name: String,
    val title: Title? = null,
    val rating: Int? = null,
    val fideId: Int? = null,
    val team: String? = null,
    val fed: String? = null,
    val score: Double? = null,
    val played: Int? = null,
    val ratingDiff: Int? = null,
    val performance: Int? = null,
    val tiebreaks: List<BroadcastPlayerTiebreak>? = null,
    val rank: Int? = null
)
