package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class TeamRequest(
    val teamId: String,
    val userId: String,
    val date: Int,
    val message: String? = null
)
