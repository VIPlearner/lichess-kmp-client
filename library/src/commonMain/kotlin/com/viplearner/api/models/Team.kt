package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Team(
    val id: String,
    val name: String,
    val description: String? = null,
    val flair: Flair? = null,
    val leader: LightUser? = null,
    val leaders: List<LightUser>? = null,
    val nbMembers: Int? = null,
    val open: Boolean? = null,
    val joined: Boolean? = null,
    val requested: Boolean? = null
)
