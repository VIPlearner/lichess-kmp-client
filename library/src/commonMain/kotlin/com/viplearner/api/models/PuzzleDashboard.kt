package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PuzzleDashboard(
    val days: Int,
    val global: PuzzlePerformance,
    val themes: Map<String, Theme>
) {
    @Serializable
    data class Theme(
        val results: PuzzlePerformance,
        val theme: String
    )
}
