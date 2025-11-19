package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ChallengeDeclinedJsonPerf(
    val icon: String,
    val name: String
)

@Serializable
enum class ChallengeDeclinedJsonDirection {
    @SerialName("in")
    IN,

    @SerialName("out")
    OUT
}

@Serializable
enum class ChallengeDeclinedJsonDeclinereasonkey {
    @SerialName("generic")
    GENERIC,

    @SerialName("later")
    LATER,

    @SerialName("toofast")
    TOOFAST,

    @SerialName("tooslow")
    TOOSLOW,

    @SerialName("timecontrol")
    TIMECONTROL,

    @SerialName("rated")
    RATED,

    @SerialName("casual")
    CASUAL,

    @SerialName("standard")
    STANDARD,

    @SerialName("variant")
    VARIANT,

    @SerialName("nobot")
    NOBOT,

    @SerialName("onlybot")
    ONLYBOT
}

@Serializable
data class ChallengeDeclinedJson(
    val id: String,
    val url: String,
    val status: ChallengeStatus,
    val challenger: ChallengeUser,
    val destUser: ChallengeUser?,
    val variant: Variant,
    val rated: Boolean,
    val speed: Speed,
    val timeControl: TimeControl,
    val color: ChallengeColor,
    val finalColor: GameColor? = null,
    val perf: ChallengeDeclinedJsonPerf,
    val direction: ChallengeDeclinedJsonDirection? = null,
    val initialFen: String? = null,
    val rematchOf: String? = null,
    val declineReason: String,
    val declineReasonKey: ChallengeDeclinedJsonDeclinereasonkey
)
