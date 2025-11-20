package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class GameStatus(
    val id: GameStatusId,
    val name: GameStatusName,
)
