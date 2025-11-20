package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Verdict(
    val condition: String,
    val verdict: String,
)
