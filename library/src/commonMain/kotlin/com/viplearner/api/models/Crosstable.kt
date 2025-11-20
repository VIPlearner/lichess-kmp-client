package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Crosstable(
    val users: Map<String, Double>,
    val nbGames: Long,
)
