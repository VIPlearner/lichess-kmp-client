package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class GameColor {
    @SerialName("white")
    WHITE,

    @SerialName("black")
    BLACK,
}
