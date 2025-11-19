package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GameStartEvent(
    val type: String,
    val game: GameEventInfo
)
