package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class TvFeedFeaturedD(
    val id: String,
    val orientation: GameColor,
    val players: List<Player>,
    val fen: String
) {
    @Serializable
    data class Player(
        val color: GameColor,
        val user: LightUser,
        val rating: Int,
        val seconds: Int
    )
}

@Serializable
data class TvFeedFeatured(
    val t: String,
    val d: TvFeedFeaturedD
)
