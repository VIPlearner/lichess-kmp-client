package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeJsonPerf(
    val icon: String,
    val name: String,
)

@Serializable
enum class ChallengeJsonDirection {
    @SerialName("in")
    IN,

    @SerialName("out")
    OUT,
}

@Serializable
data class ChallengeJson(
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
    val perf: ChallengeJsonPerf,
    val direction: ChallengeJsonDirection? = null,
    val initialFen: String? = null,
    val rematchOf: String? = null,
)
