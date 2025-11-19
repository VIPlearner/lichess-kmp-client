package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class BroadcastPlayerTiebreak(
    val extendedCode: BroadcastTiebreakExtendedCode,
    val description: String,
    val points: Double
)
