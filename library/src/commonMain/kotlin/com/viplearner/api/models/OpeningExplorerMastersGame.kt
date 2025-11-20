package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class OpeningExplorerMastersGame(
    val id: String,
    val winner: GameColor?,
    val white: OpeningExplorerGamePlayer,
    val black: OpeningExplorerGamePlayer,
    val year: Long,
    val month: String? = null,
)
