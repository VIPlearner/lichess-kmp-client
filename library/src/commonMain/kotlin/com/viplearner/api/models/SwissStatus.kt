package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class SwissStatus {
    @SerialName("created")
    CREATED,

    @SerialName("started")
    STARTED,

    @SerialName("finished")
    FINISHED
}
