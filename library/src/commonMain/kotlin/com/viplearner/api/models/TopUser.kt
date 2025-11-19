package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class TopUser(
    val id: String,
    val username: String,
    val perfs: Map<String, Map<String, Perf>>? = null,
    val title: Title? = null,
    val patron: Boolean? = null,
    val online: Boolean? = null
) {
    @Serializable
    data class Perf(
        val rating: Int,
        val progress: Int
    )
}
