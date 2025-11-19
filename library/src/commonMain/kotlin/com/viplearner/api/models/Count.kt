package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Count(
    val all: Int,
    val rated: Int,
    val ai: Int? = null,
    val draw: Int,
    val drawH: Int? = null,
    val loss: Int,
    val lossH: Int? = null,
    val win: Int,
    val winH: Int? = null,
    val bookmark: Int,
    val playing: Int,
    val import: Int,
    val me: Int
)
