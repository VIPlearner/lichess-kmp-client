package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class BroadcastTiebreakExtendedCode {
    @SerialName("AOB")
    AOB,

    @SerialName("APPO")
    APPO,

    @SerialName("APRO")
    APRO,

    @SerialName("ARO")
    ARO,

    @SerialName("ARO-C1")
    ARO_C1,

    @SerialName("ARO-C2")
    ARO_C2,

    @SerialName("ARO-M1")
    ARO_M1,

    @SerialName("ARO-M2")
    ARO_M2,

    @SerialName("BH")
    BH,

    @SerialName("BH-C1")
    BH_C1,

    @SerialName("BH-C2")
    BH_C2,

    @SerialName("BH-M1")
    BH_M1,

    @SerialName("BH-M2")
    BH_M2,

    @SerialName("BPG")
    BPG,

    @SerialName("BWG")
    BWG,

    @SerialName("DE")
    DE,

    @SerialName("FB")
    FB,

    @SerialName("FB-C1")
    FB_C1,

    @SerialName("FB-C2")
    FB_C2,

    @SerialName("FB-M1")
    FB_M1,

    @SerialName("FB-M2")
    FB_M2,

    @SerialName("KS")
    KS,

    @SerialName("PS")
    PS,

    @SerialName("PS-C1")
    PS_C1,

    @SerialName("PS-C2")
    PS_C2,

    @SerialName("PS-M1")
    PS_M1,

    @SerialName("PS-M2")
    PS_M2,

    @SerialName("PTP")
    PTP,

    @SerialName("SB")
    SB,

    @SerialName("SB-C1")
    SB_C1,

    @SerialName("SB-C2")
    SB_C2,

    @SerialName("SB-M1")
    SB_M1,

    @SerialName("SB-M2")
    SB_M2,

    @SerialName("TPR")
    TPR,

    @SerialName("WON")
    WON
}
