package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed class TvFeed {
    @Serializable
    @SerialName("TvFeedFeatured")
    data class Variant1(val value: TvFeedFeatured) : TvFeed()

    @Serializable
    @SerialName("TvFeedFen")
    data class Variant2(val value: TvFeedFen) : TvFeed()

}
