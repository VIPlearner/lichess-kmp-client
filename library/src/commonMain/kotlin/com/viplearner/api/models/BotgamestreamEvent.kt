package com.viplearner.api.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
data class BotgamestreamEventGameFullEventClock(
    val initial: Long? = null,
    val increment: Long? = null,
)

@Serializable
data class BotgamestreamEventGameFullEventPerf(
    val name: String? = null,
)

@Serializable
enum class BotgamestreamEventChatLineEventRoom {
    @SerialName("player")
    PLAYER,

    @SerialName("spectator")
    SPECTATOR,
}

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class BotgamestreamEvent {
    @Serializable
    @SerialName("gameFull")
    data class GameFullEvent(
        val type: String,
        val id: String,
        val variant: Variant,
        val clock: BotgamestreamEventGameFullEventClock? = null,
        val speed: Speed,
        val perf: BotgamestreamEventGameFullEventPerf,
        val rated: Boolean,
        val createdAt: Long,
        val white: GameEventPlayer,
        val black: GameEventPlayer,
        val initialFen: String,
        val state: GameStateEvent,
        val daysPerTurn: Int? = null,
        val tournamentId: String? = null,
    ) : BotgamestreamEvent()

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
        val btakeback: Boolean? = null,
    ) : BotgamestreamEvent()

    @Serializable
    @SerialName("chatLine")
    data class ChatLineEvent(
        val type: String,
        val room: BotgamestreamEventChatLineEventRoom,
        val username: String,
        val text: String,
    ) : BotgamestreamEvent()

    @Serializable
    @SerialName("opponentGone")
    data class OpponentGoneEvent(
        val type: String,
        val gone: Boolean,
        val claimWinInSeconds: Int? = null,
    ) : BotgamestreamEvent()
}
