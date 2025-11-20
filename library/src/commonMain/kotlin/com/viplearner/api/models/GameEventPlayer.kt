package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class GameEventPlayer(
    val aiLevel: Long? = null,
    val id: String,
    val name: String,
    val title: Title? = null,
    val rating: Long? = null,
    val provisional: Boolean? = null,
)
