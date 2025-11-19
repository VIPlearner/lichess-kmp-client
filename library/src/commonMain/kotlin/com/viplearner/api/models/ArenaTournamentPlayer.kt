package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ArenaTournamentPlayer(
    val games: Int,
    val score: Int,
    val rank: Int,
    val performance: Int? = null
)
