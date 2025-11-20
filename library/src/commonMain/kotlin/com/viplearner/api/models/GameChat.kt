package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class GameChatItem(
    val text: String,
    val user: String,
)

typealias GameChat = List<GameChatItem>
