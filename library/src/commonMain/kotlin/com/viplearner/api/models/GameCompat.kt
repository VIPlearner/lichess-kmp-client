package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GameCompat(
    val bot: Boolean? = null,
    val board: Boolean? = null
)
