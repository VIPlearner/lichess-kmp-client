package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Perf(
    val games: Int,
    val rating: Int,
    val rd: Int,
    val prog: Int,
    val prov: Boolean? = null
)
