package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PuzzleRacer(
    val id: String,
    val url: String,
)
