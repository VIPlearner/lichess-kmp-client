package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GameStatus(
    val id: GameStatusId,
    val name: GameStatusName
)
