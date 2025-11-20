package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class GamePlayerUserAnalysis(
    val inaccuracy: Long,
    val mistake: Long,
    val blunder: Long,
    val acpl: Long,
    val accuracy: Long? = null,
)

@Serializable
data class GamePlayerUser(
    val user: LightUser,
    val rating: Long,
    val ratingDiff: Long? = null,
    val name: String? = null,
    val provisional: Boolean? = null,
    val aiLevel: Long? = null,
    val analysis: GamePlayerUserAnalysis? = null,
    val team: String? = null,
)
