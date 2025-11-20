package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PuzzleDashboard(
    val days: Long,
    val global: PuzzlePerformance,
    val themes: Map<String, Theme>,
) {
    @Serializable
    data class Theme(
        val results: PuzzlePerformance,
        val theme: String,
    )
}
