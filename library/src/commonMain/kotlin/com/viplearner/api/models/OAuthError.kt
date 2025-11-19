package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class OAuthError(
    val error: String,
    @SerialName("error_description")
    val errorDescription: String? = null
)
