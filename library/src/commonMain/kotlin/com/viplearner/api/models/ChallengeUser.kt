package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ChallengeUser(
    val id: String,
    val name: String,
    val rating: Int? = null,
    val title: Title? = null,
    val flair: Flair? = null,
    val patron: Boolean? = null,
    val patronColor: PatronColor? = null,
    val provisional: Boolean? = null,
    val online: Boolean? = null,
    val lag: Int? = null
)
