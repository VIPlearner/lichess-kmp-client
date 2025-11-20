package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class OpponentGoneEvent(
    val type: String,
    val gone: Boolean,
    val claimWinInSeconds: Long? = null,
)
