package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ChallengeDeclinedEvent(
    val type: String,
    val challenge: ChallengeDeclinedJson
)
