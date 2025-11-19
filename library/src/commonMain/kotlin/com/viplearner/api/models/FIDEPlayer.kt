package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class FIDEPlayer(
    val id: Int,
    val name: String,
    val title: Title? = null,
    val federation: String,
    val year: Int? = null,
    val inactive: Int? = null,
    val standard: Int? = null,
    val rapid: Int? = null,
    val blitz: Int? = null
)
