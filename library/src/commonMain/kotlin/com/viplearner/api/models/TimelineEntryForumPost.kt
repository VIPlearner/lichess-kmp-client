package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class TimelineEntryForumPostType {
    @SerialName("forum-post")
    FORUM_POST
}

@Serializable
data class TimelineEntryForumPostData(
    val userId: String,
    val topicId: String,
    val topicName: String,
    val postId: String
)

@Serializable
data class TimelineEntryForumPost(
    val type: TimelineEntryForumPostType,
    val date: Double,
    val data: TimelineEntryForumPostData
)
