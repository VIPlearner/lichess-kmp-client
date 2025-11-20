package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class GameFinishEvent(
    val type: String,
    val game: GameEventInfo,
)
