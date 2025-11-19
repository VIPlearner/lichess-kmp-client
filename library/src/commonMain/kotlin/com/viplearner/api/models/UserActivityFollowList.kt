package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UserActivityFollowList(
    val ids: List<String>,
    val nb: Int? = null
)
