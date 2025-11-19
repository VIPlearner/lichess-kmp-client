package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class BroadcastTopPast(
    val currentPage: Int? = null,
    val maxPerPage: Int? = null,
    val currentPageResults: List<BroadcastWithLastRound>? = null,
    val previousPage: Int? = null,
    val nextPage: Int? = null
)

@Serializable
data class BroadcastTop(
    val active: List<BroadcastWithLastRound>? = null,
    val upcoming: List<BroadcastWithLastRound>? = null,
    val past: BroadcastTopPast? = null
)
