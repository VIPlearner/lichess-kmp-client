package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class BroadcastWithLastRound(
    val group: String? = null,
    val tour: BroadcastTour? = null,
    val round: BroadcastRoundInfo? = null
)
