package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastTopPast(
    val currentPage: Long? = null,
    val maxPerPage: Long? = null,
    val currentPageResults: List<BroadcastWithLastRound>? = null,
    val previousPage: Long? = null,
    val nextPage: Long? = null,
)

@Serializable
data class BroadcastTop(
    val active: List<BroadcastWithLastRound>? = null,
    val upcoming: List<BroadcastWithLastRound>? = null,
    val past: BroadcastTopPast? = null,
)
