package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class OpeningExplorerGamePlayer(
    val name: String,
    val rating: Int
)
