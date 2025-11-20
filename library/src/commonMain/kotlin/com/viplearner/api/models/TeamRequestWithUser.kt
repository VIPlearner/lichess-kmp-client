package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class TeamRequestWithUser(
    val request: TeamRequest,
    val user: User,
)
