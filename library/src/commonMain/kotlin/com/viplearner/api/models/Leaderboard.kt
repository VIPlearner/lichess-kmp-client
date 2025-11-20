package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Leaderboard(
    val users: List<TopUser>,
)
