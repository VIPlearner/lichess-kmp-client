package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class BroadcastsbyuserResponse(
    val currentPage: Int,
    val maxPerPage: Int,
    val currentPageResults: List<BroadcastByUser>,
    val nbResults: Int,
    val previousPage: Int,
    val nextPage: Int,
    val nbPages: Int
)