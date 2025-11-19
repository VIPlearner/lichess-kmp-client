package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PuzzleBatchSolveRequest(
    val solutions: List<Solution>? = null
) {
    @Serializable
    data class Solution(
        val id: String,
        val win: Boolean,
        val rated: Boolean
    )
}
