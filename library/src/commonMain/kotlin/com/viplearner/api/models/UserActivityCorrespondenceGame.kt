package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class UserActivityCorrespondenceGameOpponent(
    val user: String,
    val rating: Long,
)

@Serializable
data class UserActivityCorrespondenceGame(
    val id: String,
    val color: GameColor,
    val url: String,
    val variant: VariantKey? = null,
    val speed: String? = null,
    val perf: String? = null,
    val rated: Boolean? = null,
    val opponent: UserActivityCorrespondenceGameOpponent,
)
