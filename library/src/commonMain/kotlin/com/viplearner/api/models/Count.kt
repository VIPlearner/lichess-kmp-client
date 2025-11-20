package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Count(
    val all: Long,
    val rated: Long,
    val ai: Long? = null,
    val draw: Long,
    val drawH: Long? = null,
    val loss: Long,
    val lossH: Long? = null,
    val win: Long,
    val winH: Long? = null,
    val bookmark: Long,
    val playing: Long,
    val import: Long,
    val me: Long,
)
