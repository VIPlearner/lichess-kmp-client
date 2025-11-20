package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class GamePlayers(
    val white: GamePlayerUser,
    val black: GamePlayerUser,
)
