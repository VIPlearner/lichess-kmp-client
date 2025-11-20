package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val username: String,
    val perfs: Perfs? = null,
    val title: Title? = null,
    val flair: Flair? = null,
    val createdAt: Long? = null,
    val disabled: Boolean? = null,
    val tosViolation: Boolean? = null,
    val profile: Profile? = null,
    val seenAt: Long? = null,
    val playTime: PlayTime? = null,
    val patron: Boolean? = null,
    val patronColor: PatronColor? = null,
    val verified: Boolean? = null,
)
