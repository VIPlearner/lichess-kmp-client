package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class BroadcastPgnPush(
    val games: List<BroadcastPgnPushItem>
) {
    @Serializable
    data class BroadcastPgnPushItem(
        val tags: BroadcastPgnPushTags,
        val moves: Int? = null,
        val error: String? = null
    )
}
