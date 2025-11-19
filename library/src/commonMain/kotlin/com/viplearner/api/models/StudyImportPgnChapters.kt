package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class StudyImportPgnChapters(
    val chapters: List<Chapter>? = null
) {
    @Serializable
    data class Chapter(
        val id: String? = null,
        val name: String? = null,
        val players: List<Player>? = null,
        val status: String? = null
    ) {
        @Serializable
        data class Player(
            val name: String? = null,
            val rating: Int? = null
        )
    }

}
