package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ResultsbyswissResponse(
    val absent: Boolean? = null,
    val rank: Int,
    val points: Double,
    val tieBreak: Int,
    val rating: Int,
    val username: String,
    val title: Title? = null,
    val performance: Int
)