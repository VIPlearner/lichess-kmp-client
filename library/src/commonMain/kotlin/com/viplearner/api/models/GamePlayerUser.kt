package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GamePlayerUserAnalysis(
    val inaccuracy: Int,
    val mistake: Int,
    val blunder: Int,
    val acpl: Int,
    val accuracy: Int? = null
)

@Serializable
data class GamePlayerUser(
    val user: LightUser,
    val rating: Int,
    val ratingDiff: Int? = null,
    val name: String? = null,
    val provisional: Boolean? = null,
    val aiLevel: Int? = null,
    val analysis: GamePlayerUserAnalysis? = null,
    val team: String? = null
)
