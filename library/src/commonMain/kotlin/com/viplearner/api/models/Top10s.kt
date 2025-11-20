package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Top10s(
    val bullet: PerfTop10,
    val blitz: PerfTop10,
    val rapid: PerfTop10,
    val classical: PerfTop10,
    val ultraBullet: PerfTop10,
    val crazyhouse: PerfTop10,
    val chess960: PerfTop10,
    val kingOfTheHill: PerfTop10,
    val threeCheck: PerfTop10,
    val antichess: PerfTop10,
    val atomic: PerfTop10,
    val horde: PerfTop10,
    val racingKings: PerfTop10,
)
