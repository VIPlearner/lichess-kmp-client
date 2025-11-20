package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class GameMoveAnalysisJudgment(
    val name: String? = null,
    val comment: String? = null,
)

@Serializable
data class GameMoveAnalysis(
    val eval: Long? = null,
    val mate: Long? = null,
    val best: String? = null,
    val variation: String? = null,
    val judgment: GameMoveAnalysisJudgment? = null,
)
