package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

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
    CORRESPONDENCE
}
