package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastPlayerEntryWithFideAndGamesFide(
    val year: Long? = null,
    val ratings: Ratings? = null,
) {
    @Serializable
    data class Ratings(
        val standard: Long? = null,
        val rapid: Long? = null,
        val blitz: Long? = null,
    )
}

@Serializable
data class BroadcastPlayerEntryWithFideAndGames(
    val fide: BroadcastPlayerEntryWithFideAndGamesFide? = null,
    val games: List<BroadcastGameEntry>? = null,
    val name: String,
    val title: Title? = null,
    val rating: Long? = null,
    val fideId: Long? = null,
    val team: String? = null,
    val fed: String? = null,
    val score: Double? = null,
    val played: Long? = null,
    val ratingDiff: Long? = null,
    val performance: Long? = null,
    val tiebreaks: List<BroadcastPlayerTiebreak>? = null,
    val rank: Long? = null,
)
