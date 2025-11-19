package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class TvGame(
    val user: LightUser,
    val rating: Int,
    val gameId: String,
    val color: GameColor
)
