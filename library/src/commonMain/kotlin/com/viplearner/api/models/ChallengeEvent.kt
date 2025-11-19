package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ChallengeEvent(
    val type: String,
    val challenge: ChallengeJson,
    val compat: GameCompat? = null
)
