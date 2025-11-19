package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ChallengeOpenJsonPerf(
    val icon: String? = null,
    val name: String? = null
)

@Serializable
data class ChallengeOpenJsonOpen(
    val userIds: List<String>? = null
)

@Serializable
data class ChallengeOpenJson(
    val id: String,
    val url: String,
    val status: ChallengeStatus,
    val challenger: Unit?,
    val destUser: Unit?,
    val variant: Variant,
    val rated: Boolean,
    val speed: Speed,
    val timeControl: TimeControl,
    val color: ChallengeColor,
    val finalColor: GameColor? = null,
    val perf: ChallengeOpenJsonPerf,
    val initialFen: String? = null,
    val urlWhite: String,
    val urlBlack: String,
    val open: ChallengeOpenJsonOpen
)
