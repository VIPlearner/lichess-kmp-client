package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ChallengeDeclinedEvent(
    val type: String,
    val challenge: ChallengeDeclinedJson,
)
