package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ChatLineEventRoom {
    @SerialName("player")
    PLAYER,

    @SerialName("spectator")
    SPECTATOR,
}

@Serializable
data class ChatLineEvent(
    val type: String,
    val room: ChatLineEventRoom,
    val username: String,
    val text: String,
)
