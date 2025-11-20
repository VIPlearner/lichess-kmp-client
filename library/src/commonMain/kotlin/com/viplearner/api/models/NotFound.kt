package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class NotFound(
    val error: String,
)
