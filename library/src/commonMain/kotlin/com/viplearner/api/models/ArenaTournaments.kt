package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ArenaTournaments(
    val created: List<ArenaTournament>,
    val started: List<ArenaTournament>,
    val finished: List<ArenaTournament>
)
