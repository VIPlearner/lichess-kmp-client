package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class PerfType {
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

    @SerialName("chess960")
    CHESS960,

    @SerialName("crazyhouse")
    CRAZYHOUSE,

    @SerialName("antichess")
    ANTICHESS,

    @SerialName("atomic")
    ATOMIC,

    @SerialName("horde")
    HORDE,

    @SerialName("kingOfTheHill")
    KINGOFTHEHILL,

    @SerialName("racingKings")
    RACINGKINGS,

    @SerialName("threeCheck")
    THREECHECK
}
