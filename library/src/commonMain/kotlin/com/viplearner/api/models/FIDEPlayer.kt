package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class FIDEPlayer(
    val id: Long,
    val name: String,
    val title: Title? = null,
    val federation: String,
    val year: Long? = null,
    val inactive: Long? = null,
    val standard: Long? = null,
    val rapid: Long? = null,
    val blitz: Long? = null,
)
