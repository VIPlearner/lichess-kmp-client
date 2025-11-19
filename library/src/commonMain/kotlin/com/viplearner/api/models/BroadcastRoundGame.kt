package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class BroadcastRoundGameCheck {
    @SerialName("+")
    PLUS,

    @SerialName("#")
    HASH
}

@Serializable
enum class BroadcastRoundGameStatus {
    @SerialName("*")
    ONGOING,

    @SerialName("1-0")
    WHITE_WINS,

    @SerialName("0-1")
    BLACK_WINS,

    @SerialName("½-½")
    DRAW
}

@Serializable
data class BroadcastRoundGame(
    val id: String,
    val name: String,
    val fen: String? = null,
    val players: List<Player>? = null,
    val lastMove: String? = null,
    val check: BroadcastRoundGameCheck? = null,
    val thinkTime: Int? = null,
    val status: BroadcastRoundGameStatus? = null
) {
    @Serializable
    data class Player(
        val name: String,
        val title: String? = null,
        val rating: Int? = null,
        val fideId: Int? = null,
        val fed: String? = null,
        val clock: Int? = null
    )
}
