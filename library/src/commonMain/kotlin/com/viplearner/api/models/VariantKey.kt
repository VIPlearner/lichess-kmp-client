package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class VariantKey {
    @SerialName("standard")
    STANDARD,

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
    THREECHECK,

    @SerialName("fromPosition")
    FROMPOSITION,
}
