package com.viplearner.api.models


data class UserStatus(
    val id: String,
    val name: String,
    val flair: Flair? = null,
    val title: Title? = null,
    val online: Boolean? = null,
    val playing: Boolean? = null,
    val streaming: Boolean? = null,
    @Deprecated("patron is deprecated")
    val patron: Boolean? = null,
    val patronColor: PatronColor? = null
)
