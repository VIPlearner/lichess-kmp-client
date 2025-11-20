package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ArenaTournamentPlayer(
    val games: Long,
    val score: Long,
    val rank: Long,
    val performance: Long? = null,
)
