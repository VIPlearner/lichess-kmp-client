package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    ACCEPTED,
}
