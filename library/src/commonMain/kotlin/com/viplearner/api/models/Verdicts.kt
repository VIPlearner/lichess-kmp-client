package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Verdicts(
    val accepted: Boolean,
    val list: List<Verdict>,
)
