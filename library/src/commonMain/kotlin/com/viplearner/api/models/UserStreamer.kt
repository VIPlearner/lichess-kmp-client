package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UserStreamerTwitch(
    val channel: String? = null
)

@Serializable
data class UserStreamerYoutube(
    val channel: String? = null
)

@Serializable
data class UserStreamer(
    val twitch: UserStreamerTwitch? = null,
    val youTube: UserStreamerYoutube? = null
)
