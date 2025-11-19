package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class TimelineEntryBlogPostType {
    @SerialName("blog-post")
    BLOG_POST
}

@Serializable
data class TimelineEntryBlogPostData(
    val id: String,
    val slug: String,
    val title: String
)

@Serializable
data class TimelineEntryBlogPost(
    val type: TimelineEntryBlogPostType,
    val date: Double,
    val data: TimelineEntryBlogPostData
)
