package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class OpponentGoneEvent(
    val type: String,
    val gone: Boolean,
    val claimWinInSeconds: Int? = null
)
