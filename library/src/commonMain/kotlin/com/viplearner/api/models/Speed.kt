package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Speed {
    @SerialName("ultraBullet")
    ULTRABULLET,

    @SerialName("bullet")
    BULLET,

    @SerialName("blitz")
    BLITZ,

    @SerialName("rapid")
    RAPID,

    @SerialName("classical")
    CLASSICAL,

    @SerialName("correspondence")
    CORRESPONDENCE,
}
