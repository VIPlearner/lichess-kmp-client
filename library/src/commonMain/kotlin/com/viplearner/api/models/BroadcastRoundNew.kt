package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastRoundNew(
    val round: BroadcastRoundInfo,
    val tour: BroadcastTour,
    val study: BroadcastRoundStudyInfo,
)
