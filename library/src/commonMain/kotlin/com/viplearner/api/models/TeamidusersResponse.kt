package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class TeamidusersResponse(
    val joinedTeamAt: Long? = null,
    val id: String,
    val name: String,
    val title: Title? = null,
    val patronColor: PatronColor? = null
)