package com.viplearner.api.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
data class BoardgamestreamEventGameFullEventClock(
    val initial: Long? = null,
    val increment: Long? = null
)

@Serializable
data class BoardgamestreamEventGameFullEventPerf(
    val name: String? = null
)

@Serializable
enum class BoardgamestreamEventChatLineEventRoom {
    @SerialName("player")
    PLAYER,

    @SerialName("spectator")
    SPECTATOR
}


@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class BoardgamestreamEvent {
    @Serializable
    @SerialName("gameFull")
    data class GameFullEvent(
        val type: String,
        val id: String,
        val variant: Variant,
        val clock: BoardgamestreamEventGameFullEventClock? = null,
        val speed: Speed,
        val perf: BoardgamestreamEventGameFullEventPerf,
        val rated: Boolean,
        val createdAt: Long,
        val white: GameEventPlayer,
        val black: GameEventPlayer,
        val initialFen: String,
        val state: GameStateEvent,
        val daysPerTurn: Int? = null,
        val tournamentId: String? = null
    ) : BoardgamestreamEvent()

    @Serializable
    @SerialName("gameState")
    data class GameStateEvent(
        val type: String,
        val moves: String,
        val wtime: Int,
        val btime: Int,
        val winc: Int,
        val binc: Int,
        val status: GameStatusName,
        val winner: GameColor? = null,
        val wdraw: Boolean? = null,
        val bdraw: Boolean? = null,
        val wtakeback: Boolean? = null,
        val btakeback: Boolean? = null
    ) : BoardgamestreamEvent()

    @Serializable
    @SerialName("chatLine")
    data class ChatLineEvent(
        val type: String,
        val room: BoardgamestreamEventChatLineEventRoom,
        val username: String,
        val text: String
    ) : BoardgamestreamEvent()

    @Serializable
    @SerialName("opponentGone")
    data class OpponentGoneEvent(
        val type: String,
        val gone: Boolean,
        val claimWinInSeconds: Int? = null
    ) : BoardgamestreamEvent()
}
