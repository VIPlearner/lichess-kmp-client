package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastPgnPush(
    val games: List<BroadcastPgnPushItem>,
) {
    @Serializable
    data class BroadcastPgnPushItem(
        val tags: BroadcastPgnPushTags,
        val moves: Long? = null,
        val error: String? = null,
    )
}
