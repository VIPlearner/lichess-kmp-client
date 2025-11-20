package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BulkPairing(
    val id: String,
    val games: List<Game>,
    val variant: VariantKey,
    val clock: Clock,
    val pairAt: Long,
    val pairedAt: Long?,
    val rated: Boolean,
    val startClocksAt: Long,
    val scheduledAt: Long,
) {
    @Serializable
    data class Game(
        val id: String,
        val black: String,
        val white: String,
    )
}
