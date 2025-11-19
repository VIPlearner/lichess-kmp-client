package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class OpeningExplorerPlayerGameMode {
    @SerialName("rated")
    RATED,

    @SerialName("casual")
    CASUAL
}

@Serializable
data class OpeningExplorerPlayerGame(
    val id: String,
    val winner: GameColor?,
    val speed: Speed,
    val mode: OpeningExplorerPlayerGameMode,
    val white: OpeningExplorerGamePlayer,
    val black: OpeningExplorerGamePlayer,
    val year: Int,
    val month: String
)
