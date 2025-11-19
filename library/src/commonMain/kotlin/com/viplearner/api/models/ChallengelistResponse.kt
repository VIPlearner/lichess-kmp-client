package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ChallengelistResponse(
    @SerialName("in")
    val incomingChallenges: List<ChallengeJson>? = null,
    @SerialName("out")
    val outgoingChallenges: List<ChallengeJson>? = null
)
