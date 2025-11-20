package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class StudyMetadata(
    val id: String,
    val name: String,
    val createdAt: Long,
    val updatedAt: Long,
)
