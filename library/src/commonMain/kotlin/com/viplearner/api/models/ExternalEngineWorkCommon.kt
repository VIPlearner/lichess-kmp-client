package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ExternalEngineWorkCommon(
    val sessionId: String,
    val threads: Long,
    val hash: Long,
    val multiPv: Long,
    val variant: UciVariant,
    val initialFen: String,
    val moves: List<String>,
)
