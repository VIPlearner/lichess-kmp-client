package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ArenaTournamentPlayed(
    val tournament: ArenaTournament,
    val player: ArenaTournamentPlayer,
)
