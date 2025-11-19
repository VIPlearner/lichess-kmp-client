package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class GameSource {
    @SerialName("lobby")
    LOBBY,

    @SerialName("friend")
    FRIEND,

    @SerialName("ai")
    AI,

    @SerialName("api")
    API,

    @SerialName("tournament")
    TOURNAMENT,

    @SerialName("position")
    POSITION,

    @SerialName("import")
    IMPORT,

    @SerialName("importlive")
    IMPORTLIVE,

    @SerialName("simul")
    SIMUL,

    @SerialName("relay")
    RELAY,

    @SerialName("pool")
    POOL,

    @SerialName("arena")
    ARENA,

    @SerialName("swiss")
    SWISS
}
