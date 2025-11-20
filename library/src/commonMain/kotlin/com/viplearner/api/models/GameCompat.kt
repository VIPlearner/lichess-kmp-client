package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class GameCompat(
    val bot: Boolean? = null,
    val board: Boolean? = null,
)
