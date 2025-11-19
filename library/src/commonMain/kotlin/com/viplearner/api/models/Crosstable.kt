package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Crosstable(
    val users: Map<String, Int>,
    val nbGames: Int
)
