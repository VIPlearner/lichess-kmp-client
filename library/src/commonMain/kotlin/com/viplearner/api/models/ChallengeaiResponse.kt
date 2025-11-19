package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ChallengeaiResponse(
    val id: String,
    val variant: Variant? = null,
    val speed: Speed? = null,
    val perf: PerfType? = null,
    val rated: Boolean? = null,
    val fen: String? = null,
    val turns: Int? = null,
    val source: GameSource? = null,
    val status: GameStatus? = null,
    val createdAt: Long? = null,
    val player: GameColor? = null,
    val fullId: String? = null
)

