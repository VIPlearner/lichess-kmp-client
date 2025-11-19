package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class OpeningExplorerMastersGame(
    val id: String,
    val winner: GameColor?,
    val white: OpeningExplorerGamePlayer,
    val black: OpeningExplorerGamePlayer,
    val year: Int,
    val month: String? = null
)
