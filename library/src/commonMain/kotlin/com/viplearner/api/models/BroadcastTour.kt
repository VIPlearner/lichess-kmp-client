package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastTourInfo(
    val website: String? = null,
    val players: String? = null,
    val location: String? = null,
    val tc: String? = null,
    val fideTc: String? = null,
    val timeZone: String? = null,
    val standings: String? = null,
    val format: String? = null,
)

@Serializable
data class BroadcastTour(
    val id: String,
    val name: String,
    val slug: String,
    val createdAt: Long,
    val dates: List<Long>? = null,
    val info: BroadcastTourInfo? = null,
    val tier: Long? = null,
    val image: String? = null,
    val description: String? = null,
    val leaderboard: Boolean? = null,
    val teamTable: Boolean? = null,
    val url: String,
    val communityOwner: LightUser? = null,
)
