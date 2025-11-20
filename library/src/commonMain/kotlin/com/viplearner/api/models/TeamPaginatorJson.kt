package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class TeamPaginatorJson(
    val currentPage: Long,
    val maxPerPage: Long,
    val currentPageResults: List<Team>,
    val previousPage: Long?,
    val nextPage: Long?,
    val nbResults: Long,
    val nbPages: Long,
)
