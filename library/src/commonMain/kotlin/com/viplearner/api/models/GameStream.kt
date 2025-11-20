package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class GameStream(
    val items: List<GameStreamGame>,
)
