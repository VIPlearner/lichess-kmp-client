package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class TeamRequestWithUser(
    val request: TeamRequest,
    val user: User
)
