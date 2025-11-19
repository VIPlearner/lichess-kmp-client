package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class TeamPaginatorJson(
    val currentPage: Int,
    val maxPerPage: Int,
    val currentPageResults: List<Team>,
    val previousPage: Int?,
    val nextPage: Int?,
    val nbResults: Int,
    val nbPages: Int
)
