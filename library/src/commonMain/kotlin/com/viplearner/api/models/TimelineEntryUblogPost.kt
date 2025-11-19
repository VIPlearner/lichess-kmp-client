package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class TimelineEntryUblogPostType {
    @SerialName("ublog-post")
    UBLOG_POST
}

@Serializable
data class TimelineEntryUblogPostData(
    val userId: String,
    val id: String,
    val slug: String,
    val title: String
)

@Serializable
data class TimelineEntryUblogPost(
    val type: TimelineEntryUblogPostType,
    val date: Double,
    val data: TimelineEntryUblogPostData
)
