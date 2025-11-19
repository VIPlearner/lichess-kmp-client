package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UserActivityScoreRp(
    val before: Int,
    val after: Int
)

@Serializable
data class UserActivityScore(
    val win: Int,
    val loss: Int,
    val draw: Int,
    val rp: UserActivityScoreRp
)
