package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class UserNote(
    val from: LightUser? = null,
    val to: LightUser? = null,
    val text: String? = null,
    val date: Long? = null,
)
