package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ExternalEngineWorkCommon(
    val sessionId: String,
    val threads: Int,
    val hash: Int,
    val multiPv: Int,
    val variant: UciVariant,
    val initialFen: String,
    val moves: List<String>
)
