package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastssearchResponse(
    val currentPage: Int,
    val maxPerPage: Int,
    val currentPageResults: List<BroadcastWithLastRound>,
    val previousPage: Int?,
    val nextPage: Int?,
)
