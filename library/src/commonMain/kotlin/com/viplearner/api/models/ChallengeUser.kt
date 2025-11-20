package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ChallengeUser(
    val id: String,
    val name: String,
    val rating: Long? = null,
    val title: Title? = null,
    val flair: Flair? = null,
    val patron: Boolean? = null,
    val patronColor: PatronColor? = null,
    val provisional: Boolean? = null,
    val online: Boolean? = null,
    val lag: Long? = null,
)
