package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class GameStatusName {
    @SerialName("created")
    CREATED,

    @SerialName("started")
    STARTED,

    @SerialName("aborted")
    ABORTED,

    @SerialName("mate")
    MATE,

    @SerialName("resign")
    RESIGN,

    @SerialName("stalemate")
    STALEMATE,

    @SerialName("timeout")
    TIMEOUT,

    @SerialName("draw")
    DRAW,

    @SerialName("outoftime")
    OUTOFTIME,

    @SerialName("cheat")
    CHEAT,

    @SerialName("noStart")
    NOSTART,

    @SerialName("unknownFinish")
    UNKNOWNFINISH,

    @SerialName("insufficientMaterialClaim")
    INSUFFICIENTMATERIALCLAIM,

    @SerialName("variantEnd")
    VARIANTEND
}
