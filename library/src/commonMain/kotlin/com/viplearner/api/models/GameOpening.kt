package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GameOpening(
    val eco: String,
    val name: String,
    val ply: Int
)
