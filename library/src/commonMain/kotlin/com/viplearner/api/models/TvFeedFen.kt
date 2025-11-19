package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class TvFeedFenD(
    val fen: String,
    val lm: String,
    val wc: Int,
    val bc: Int
)

@Serializable
data class TvFeedFen(
    val t: String,
    val d: TvFeedFenD
)
