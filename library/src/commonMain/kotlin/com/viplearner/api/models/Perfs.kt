package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Perfs(
    val chess960: Perf? = null,
    val atomic: Perf? = null,
    val racingKings: Perf? = null,
    val ultraBullet: Perf? = null,
    val blitz: Perf? = null,
    val kingOfTheHill: Perf? = null,
    val threeCheck: Perf? = null,
    val antichess: Perf? = null,
    val crazyhouse: Perf? = null,
    val bullet: Perf? = null,
    val correspondence: Perf? = null,
    val horde: Perf? = null,
    val puzzle: Perf? = null,
    val classical: Perf? = null,
    val rapid: Perf? = null,
    val storm: PuzzleModePerf? = null,
    val racer: PuzzleModePerf? = null,
    val streak: PuzzleModePerf? = null
)
