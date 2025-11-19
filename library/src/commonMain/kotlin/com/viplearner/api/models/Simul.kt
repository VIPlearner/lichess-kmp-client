package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Simul(
    val id: String,
    val host: Host,
    val name: String,
    val fullName: String,
    val variants: List<Variant>,
    val isCreated: Boolean,
    val isFinished: Boolean,
    val isRunning: Boolean,
    val text: String? = null,
    val estimatedStartAt: Int? = null,
    val startedAt: Int? = null,
    val finishedAt: Int? = null,
    val nbApplicants: Int,
    val nbPairings: Int
) {
    @Serializable
    data class Variant(
        val key: VariantKey,
        val icon: String,
        val name: String
    )

    @Serializable
    data class Host(
        val id: String,
        val name: String,
        val flair: Flair? = null,
        val title: Title? = null,
        val patron: Boolean? = null,
        val patronColor: PatronColor? = null,
        val rating: Int? = null,
        val provisional: Boolean? = null,
        val gameId: String? = null,
        val online: Boolean? = null
    )

}
