package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class ChatLineEventRoom {
    @SerialName("player")
    PLAYER,

    @SerialName("spectator")
    SPECTATOR
}

@Serializable
data class ChatLineEvent(
    val type: String,
    val room: ChatLineEventRoom,
    val username: String,
    val text: String
)
