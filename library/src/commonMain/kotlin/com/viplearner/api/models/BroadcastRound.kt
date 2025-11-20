package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastRound(
    val round: BroadcastRoundInfo,
    val tour: BroadcastTour,
    val study: BroadcastRoundStudyInfo,
    val games: List<BroadcastRoundGame>,
    val group: BroadcastGroup? = null,
    val isSubscribed: Boolean? = null,
)
