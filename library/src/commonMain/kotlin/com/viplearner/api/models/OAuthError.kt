package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OAuthError(
    val error: String,
    @SerialName("error_description")
    val errorDescription: String? = null,
)
