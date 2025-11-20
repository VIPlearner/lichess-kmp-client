package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class SwissUnauthorisedEdit(
    val error: String? = null,
)
