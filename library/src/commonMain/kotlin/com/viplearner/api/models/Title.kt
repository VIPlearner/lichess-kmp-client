package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Title {
    @SerialName("GM")
    GM,

    @SerialName("WGM")
    WGM,

    @SerialName("IM")
    IM,

    @SerialName("WIM")
    WIM,

    @SerialName("FM")
    FM,

    @SerialName("WFM")
    WFM,

    @SerialName("NM")
    NM,

    @SerialName("CM")
    CM,

    @SerialName("WCM")
    WCM,

    @SerialName("WNM")
    WNM,

    @SerialName("LM")
    LM,

    @SerialName("BOT")
    BOT,
}
