package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
open class OpeningExplorerLichessGame(
    val id: String,
    val winner: GameColor?,
    val speed: Speed? = null,
    val white: OpeningExplorerGamePlayer,
    val black: OpeningExplorerGamePlayer,
    val year: Double,
    val month: String?,
)
