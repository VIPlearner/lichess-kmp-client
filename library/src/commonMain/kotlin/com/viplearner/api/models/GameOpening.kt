package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class GameOpening(
    val eco: String,
    val name: String,
    val ply: Long,
)
