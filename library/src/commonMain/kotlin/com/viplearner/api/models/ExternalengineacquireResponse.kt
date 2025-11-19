package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ExternalengineacquireResponse(
    val id: String,
    val work: ExternalEngineWork,
    val engine: ExternalEngine
)