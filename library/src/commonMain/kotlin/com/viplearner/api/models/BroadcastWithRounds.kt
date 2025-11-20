package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastWithRounds(
    val tour: BroadcastTour,
    val group: BroadcastGroup? = null,
    val rounds: List<BroadcastRoundInfo>,
    val defaultRoundId: String? = null,
)
