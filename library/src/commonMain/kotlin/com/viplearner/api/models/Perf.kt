package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Perf(
    val games: Long,
    val rating: Long,
    val rd: Long,
    val prog: Long,
    val prov: Boolean? = null,
)
