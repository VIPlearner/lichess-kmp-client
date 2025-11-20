package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastRoundStudyInfoFeatures(
    val chat: Boolean? = null,
    val computer: Boolean? = null,
    val explorer: Boolean? = null,
)

@Serializable
data class BroadcastRoundStudyInfo(
    val writeable: Boolean? = null,
    val features: BroadcastRoundStudyInfoFeatures? = null,
)
