package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class ChallengeStatus {
    @SerialName("created")
    CREATED,

    @SerialName("offline")
    OFFLINE,

    @SerialName("canceled")
    CANCELED,

    @SerialName("declined")
    DECLINED,

    @SerialName("accepted")
    ACCEPTED
}
