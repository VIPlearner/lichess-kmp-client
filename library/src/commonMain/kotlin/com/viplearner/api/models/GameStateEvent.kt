package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class GameStateEvent(
    val type: String,
    val moves: String,
    val wtime: Long,
    val btime: Long,
    val winc: Long,
    val binc: Long,
    val status: GameStatusName,
    val winner: GameColor? = null,
    val wdraw: Boolean? = null,
    val bdraw: Boolean? = null,
    val wtakeback: Boolean? = null,
    val btakeback: Boolean? = null,
)
