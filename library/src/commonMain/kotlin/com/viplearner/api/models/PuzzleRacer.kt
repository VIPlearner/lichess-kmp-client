package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PuzzleRacer(
    val id: String,
    val url: String
)
