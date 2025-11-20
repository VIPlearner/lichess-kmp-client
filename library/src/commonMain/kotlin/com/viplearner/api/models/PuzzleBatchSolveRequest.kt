package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PuzzleBatchSolveRequest(
    val solutions: List<Solution>? = null,
) {
    @Serializable
    data class Solution(
        val id: String,
        val win: Boolean,
        val rated: Boolean,
    )
}
