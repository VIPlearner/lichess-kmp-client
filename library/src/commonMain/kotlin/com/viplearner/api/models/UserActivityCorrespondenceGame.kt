package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UserActivityCorrespondenceGameOpponent(
    val user: String,
    val rating: Int
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
    val opponent: UserActivityCorrespondenceGameOpponent
)
