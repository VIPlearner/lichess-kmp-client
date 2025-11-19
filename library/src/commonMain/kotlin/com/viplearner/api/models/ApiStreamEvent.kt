package com.viplearner.api.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator


@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class ApiStreamEvent {
    @Serializable
    @SerialName("gameStart")
    data class GameStartEvent(
        val type: String,
        val game: GameEventInfo
    ) : ApiStreamEvent()

    @Serializable
    @SerialName("gameFinish")
    data class GameFinishEvent(
        val type: String,
        val game: GameEventInfo
    ) : ApiStreamEvent()

    @Serializable
    @SerialName("challenge")
    data class ChallengeEvent(
        val type: String,
        val challenge: ChallengeJson,
        val compat: GameCompat? = null
    ) : ApiStreamEvent()

    @Serializable
    @SerialName("challengeCanceled")
    data class ChallengeCanceledEvent(
        val type: String,
        val challenge: ChallengeJson
    ) : ApiStreamEvent()

    @Serializable
    @SerialName("challengeDeclined")
    data class ChallengeDeclinedEvent(
        val type: String,
        val challenge: ChallengeDeclinedJson
    ) : ApiStreamEvent()
}
