package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class OpeningExplorerGamePlayer(
    val name: String,
    val rating: Long,
)
