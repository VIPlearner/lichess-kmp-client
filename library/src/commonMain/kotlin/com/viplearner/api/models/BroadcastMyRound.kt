package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class BroadcastMyRound(
    val round: BroadcastRoundInfo,
    val tour: BroadcastTour,
    val study: BroadcastRoundStudyInfo
)
