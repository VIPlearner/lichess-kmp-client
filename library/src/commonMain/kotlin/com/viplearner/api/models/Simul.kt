package com.viplearner.api.models

import kotlinx.serialization.Serializable

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
    val estimatedStartAt: Long? = null,
    val startedAt: Long? = null,
    val finishedAt: Long? = null,
    val nbApplicants: Long,
    val nbPairings: Long,
) {
    @Serializable
    data class Variant(
        val key: VariantKey,
        val icon: String,
        val name: String,
    )

    @Serializable
    data class Host(
        val id: String,
        val name: String,
        val flair: Flair? = null,
        val title: Title? = null,
        val patron: Boolean? = null,
        val patronColor: PatronColor? = null,
        val rating: Long? = null,
        val provisional: Boolean? = null,
        val gameId: String? = null,
        val online: Boolean? = null,
    )
}
