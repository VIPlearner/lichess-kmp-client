package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GamePlayers(
    val white: GamePlayerUser,
    val black: GamePlayerUser
)
