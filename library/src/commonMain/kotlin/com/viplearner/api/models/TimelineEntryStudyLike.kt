package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class TimelineEntryStudyLikeType {
    @SerialName("study-like")
    STUDY_LIKE
}

@Serializable
data class TimelineEntryStudyLikeData(
    val userId: String,
    val studyId: String,
    val studyName: String
)

@Serializable
data class TimelineEntryStudyLike(
    val type: TimelineEntryStudyLikeType,
    val date: Double,
    val data: TimelineEntryStudyLikeData
)
