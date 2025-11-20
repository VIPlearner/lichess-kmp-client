package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class TeamRequest(
    val teamId: String,
    val userId: String,
    val date: Long,
    val message: String? = null,
)
