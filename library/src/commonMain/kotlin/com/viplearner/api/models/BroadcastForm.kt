package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class BroadcastFormVisibility {
    @SerialName("public")
    PUBLIC,

    @SerialName("unlisted")
    UNLISTED,

    @SerialName("private")
    PRIVATE,
}

@Serializable
data class BroadcastForm(
    val name: String,
    @SerialName("info.format")
    val infoFormat: String? = null,
    @SerialName("info.location")
    val infoLocation: String? = null,
    @SerialName("info.tc")
    val infoTc: String? = null,
    @SerialName("info.fideTc")
    val infoFidetc: InfoFideTc? = null,
    @SerialName("info.timeZone")
    val infoTimezone: String? = null,
    @SerialName("info.players")
    val infoPlayers: String? = null,
    @SerialName("info.website")
    val infoWebsite: String? = null,
    @SerialName("info.standings")
    val infoStandings: String? = null,
    val markdown: String? = null,
    val showScores: Boolean? = null,
    val showRatingDiffs: Boolean? = null,
    val teamTable: Boolean? = null,
    val visibility: BroadcastFormVisibility? = null,
    val players: String? = null,
    val teams: String? = null,
    val tier: Long? = null,
    val tiebreaks: List<BroadcastTiebreakExtendedCode>? = null,
) {
    @Serializable
    enum class InfoFideTc {
        @SerialName("standard")
        STANDARD,

        @SerialName("rapid")
        RAPID,

        @SerialName("blitz")
        BLITZ,
    }
}
