package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TimelineEntryStreamStartType {
    @SerialName("stream-start")
    STREAM_START,
}

@Serializable
data class TimelineEntryStreamStartData(
    val id: String,
    val title: String? = null,
)

@Serializable
data class TimelineEntryStreamStart(
    val type: TimelineEntryStreamStartType,
    val date: Double,
    val data: TimelineEntryStreamStartData,
)
