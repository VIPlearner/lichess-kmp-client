package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Verdict(
    val condition: String,
    val verdict: String
)
