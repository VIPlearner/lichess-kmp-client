package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class BulkPairing(
    val id: String,
    val games: List<Game>,
    val variant: VariantKey,
    val clock: Clock,
    val pairAt: Int,
    val pairedAt: Int?,
    val rated: Boolean,
    val startClocksAt: Int,
    val scheduledAt: Int
) {
    @Serializable
    data class Game(
        val id: String,
        val black: String,
        val white: String
    )
}
