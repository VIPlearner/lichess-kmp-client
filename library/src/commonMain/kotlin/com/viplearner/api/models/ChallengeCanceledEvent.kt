package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ChallengeCanceledEvent(
    val type: String,
    val challenge: ChallengeJson,
)
