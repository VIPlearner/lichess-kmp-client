package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class GameStartEvent(
    val type: String,
    val game: GameEventInfo,
)
