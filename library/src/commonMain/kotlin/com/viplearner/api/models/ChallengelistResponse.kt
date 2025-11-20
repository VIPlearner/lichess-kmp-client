package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengelistResponse(
    @SerialName("in")
    val incomingChallenges: List<ChallengeJson>? = null,
    @SerialName("out")
    val outgoingChallenges: List<ChallengeJson>? = null,
)
