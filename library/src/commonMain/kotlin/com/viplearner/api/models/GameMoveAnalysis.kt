package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GameMoveAnalysisJudgment(
    val name: String? = null,
    val comment: String? = null
)

@Serializable
data class GameMoveAnalysis(
    val eval: Int? = null,
    val mate: Int? = null,
    val best: String? = null,
    val variation: String? = null,
    val judgment: GameMoveAnalysisJudgment? = null
)
