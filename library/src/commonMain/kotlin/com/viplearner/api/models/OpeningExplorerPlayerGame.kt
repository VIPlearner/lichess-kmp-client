package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class OpeningExplorerPlayerGameMode {
    @SerialName("rated")
    RATED,

    @SerialName("casual")
    CASUAL,
}

@Serializable
data class OpeningExplorerPlayerGame(
    val id: String,
    val winner: GameColor?,
    val speed: Speed,
    val mode: OpeningExplorerPlayerGameMode,
    val white: OpeningExplorerGamePlayer,
    val black: OpeningExplorerGamePlayer,
    val year: Long,
    val month: String,
)
