package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val flag: String? = null,
    val location: String? = null,
    val bio: String? = null,
    val realName: String? = null,
    val fideRating: Long? = null,
    val uscfRating: Long? = null,
    val ecfRating: Long? = null,
    val cfcRating: Long? = null,
    val rcfRating: Long? = null,
    val dsbRating: Long? = null,
    val links: String? = null,
)
