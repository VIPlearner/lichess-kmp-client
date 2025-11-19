package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class TimelineEntryUblogPostLikeType {
    @SerialName("ublog-post-like")
    UBLOG_POST_LIKE
}

@Serializable
data class TimelineEntryUblogPostLikeData(
    val userId: String,
    val id: String,
    val title: String
)

@Serializable
data class TimelineEntryUblogPostLike(
    val type: TimelineEntryUblogPostLikeType,
    val date: Double,
    val data: TimelineEntryUblogPostLikeData
)
