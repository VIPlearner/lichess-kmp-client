package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class TvFeedFenD(
    val fen: String,
    val lm: String,
    val wc: Long,
    val bc: Long,
)

@Serializable
data class TvFeedFen(
    val t: String,
    val d: TvFeedFenD,
)
