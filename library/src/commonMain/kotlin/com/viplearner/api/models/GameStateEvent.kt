package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GameStateEvent(
    val type: String,
    val moves: String,
    val wtime: Int,
    val btime: Int,
    val winc: Int,
    val binc: Int,
    val status: GameStatusName,
    val winner: GameColor? = null,
    val wdraw: Boolean? = null,
    val bdraw: Boolean? = null,
    val wtakeback: Boolean? = null,
    val btakeback: Boolean? = null
)
