package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ResultsbytournamentResponse(
    val rank: Int,
    val score: Int,
    val rating: Int,
    val username: String,
    val performance: Int,
    val title: Title? = null,
    val team: String? = null,
    val flair: Flair? = null,
    val sheet: ArenaSheet? = null
)