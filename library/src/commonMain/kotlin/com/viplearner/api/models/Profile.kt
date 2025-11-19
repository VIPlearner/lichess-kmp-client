package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Profile(
    val flag: String? = null,
    val location: String? = null,
    val bio: String? = null,
    val realName: String? = null,
    val fideRating: Int? = null,
    val uscfRating: Int? = null,
    val ecfRating: Int? = null,
    val cfcRating: Int? = null,
    val rcfRating: Int? = null,
    val dsbRating: Int? = null,
    val links: String? = null
)
