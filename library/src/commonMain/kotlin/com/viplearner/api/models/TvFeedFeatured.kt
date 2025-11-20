package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class TvFeedFeaturedD(
    val id: String,
    val orientation: GameColor,
    val players: List<Player>,
    val fen: String,
) {
    @Serializable
    data class Player(
        val color: GameColor,
        val user: LightUser,
        val rating: Long,
        val seconds: Long,
    )
}

@Serializable
data class TvFeedFeatured(
    val t: String,
    val d: TvFeedFeaturedD,
)
