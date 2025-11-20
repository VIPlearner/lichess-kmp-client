package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ArenaStatusName {
    @SerialName("created")
    CREATED,

    @SerialName("started")
    STARTED,

    @SerialName("finished")
    FINISHED,
}
