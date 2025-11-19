package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class OpeningExplorerOpening(
    val eco: String,
    val name: String
)
