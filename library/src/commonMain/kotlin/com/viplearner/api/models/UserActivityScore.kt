package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class UserActivityScoreRp(
    val before: Long,
    val after: Long,
)

@Serializable
data class UserActivityScore(
    val win: Long,
    val loss: Long,
    val draw: Long,
    val rp: UserActivityScoreRp,
)
