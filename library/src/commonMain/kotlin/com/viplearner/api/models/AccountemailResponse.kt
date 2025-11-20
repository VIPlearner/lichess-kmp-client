package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class AccountemailResponse(
    val email: String? = null,
)
