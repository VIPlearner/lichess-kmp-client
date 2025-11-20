package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val error: String,
)
