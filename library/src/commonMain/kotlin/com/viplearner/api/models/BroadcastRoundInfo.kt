package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastRoundInfo(
    val id: String,
    val name: String,
    val slug: String,
    val createdAt: Long,
    val rated: Boolean,
    val ongoing: Boolean? = null,
    val startsAt: Long? = null,
    val startsAfterPrevious: Boolean? = null,
    val finishedAt: Long? = null,
    val finished: Boolean? = null,
    val url: String,
    val delay: Long? = null,
    val customScoring: BroadcastCustomScoring? = null,
)
