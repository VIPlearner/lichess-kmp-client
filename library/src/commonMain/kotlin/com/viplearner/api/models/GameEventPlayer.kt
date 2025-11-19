package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GameEventPlayer(
    val aiLevel: Int? = null,
    val id: String,
    val name: String,
    val title: Title? = null,
    val rating: Int? = null,
    val provisional: Boolean? = null
)
