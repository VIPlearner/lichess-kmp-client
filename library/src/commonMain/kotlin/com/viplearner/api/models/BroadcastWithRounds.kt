package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class BroadcastWithRounds(
    val tour: BroadcastTour,
    val group: BroadcastGroup? = null,
    val rounds: List<BroadcastRoundInfo>,
    val defaultRoundId: String? = null
)
