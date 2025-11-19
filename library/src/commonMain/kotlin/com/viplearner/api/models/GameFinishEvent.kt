package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GameFinishEvent(
    val type: String,
    val game: GameEventInfo
)
