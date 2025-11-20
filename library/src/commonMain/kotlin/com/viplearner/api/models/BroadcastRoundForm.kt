package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class BroadcastRoundFormStatus {
    @SerialName("new")
    NEW,

    @SerialName("started")
    STARTED,

    @SerialName("finished")
    FINISHED,
}

@Serializable
data class BroadcastRoundForm(
    val startsAt: Long? = null,
    val startsAfterPrevious: Boolean? = null,
    val delay: Long? = null,
    val status: BroadcastRoundFormStatus? = null,
    val rated: Boolean? = null,
    @SerialName("customScoring.white.win")
    val customScoringWhiteWin: BroadcastCustomPoints? = null,
    @SerialName("customScoring.white.draw")
    val customScoringWhiteDraw: BroadcastCustomPoints? = null,
    @SerialName("customScoring.black.win")
    val customScoringBlackWin: BroadcastCustomPoints? = null,
    @SerialName("customScoring.black.draw")
    val customScoringBlackDraw: BroadcastCustomPoints? = null,
    val period: Long? = null,
)
