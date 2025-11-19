package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class GameColor {
    @SerialName("white")
    WHITE,

    @SerialName("black")
    BLACK
}
