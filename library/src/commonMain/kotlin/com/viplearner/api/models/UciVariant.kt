package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class UciVariant {
    @SerialName("chess")
    CHESS,

    @SerialName("crazyhouse")
    CRAZYHOUSE,

    @SerialName("antichess")
    ANTICHESS,

    @SerialName("atomic")
    ATOMIC,

    @SerialName("horde")
    HORDE,

    @SerialName("kingofthehill")
    KINGOFTHEHILL,

    @SerialName("racingkings")
    RACINGKINGS,

    @SerialName("3check")
    VALUE_3CHECK
}
