package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class TvGame(
    val user: LightUser,
    val rating: Long,
    val gameId: String,
    val color: GameColor,
)
