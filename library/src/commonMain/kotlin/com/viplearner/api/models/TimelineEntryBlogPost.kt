package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TimelineEntryBlogPostType {
    @SerialName("blog-post")
    BLOG_POST,
}

@Serializable
data class TimelineEntryBlogPostData(
    val id: String,
    val slug: String,
    val title: String,
)

@Serializable
data class TimelineEntryBlogPost(
    val type: TimelineEntryBlogPostType,
    val date: Double,
    val data: TimelineEntryBlogPostData,
)
