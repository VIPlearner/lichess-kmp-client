package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class UserActivityFollowList(
    val ids: List<String>,
    val nb: Long? = null,
)
