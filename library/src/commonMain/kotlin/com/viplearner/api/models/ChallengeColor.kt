package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ChallengeColor {
    @SerialName("white")
    WHITE,

    @SerialName("black")
    BLACK,

    @SerialName("random")
    RANDOM,
}
