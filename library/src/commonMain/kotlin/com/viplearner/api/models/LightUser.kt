package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class LightUser(
    val id: String,
    val name: String,
    val flair: Flair? = null,
    val title: Title? = null,
    val patron: Boolean? = null,
    val patronColor: PatronColor? = null
)
