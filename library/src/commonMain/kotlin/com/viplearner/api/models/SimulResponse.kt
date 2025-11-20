package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class SimulResponse(
    val pending: List<Simul>? = null,
    val created: List<Simul>? = null,
    val started: List<Simul>? = null,
    val finished: List<Simul>? = null,
)
