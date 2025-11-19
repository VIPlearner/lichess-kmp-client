package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class TablebaseJsonCategory {
    @SerialName("win")
    WIN,

    @SerialName("unknown")
    UNKNOWN,

    @SerialName("syzygy-win")
    SYZYGY_WIN,

    @SerialName("maybe-win")
    MAYBE_WIN,

    @SerialName("cursed-win")
    CURSED_WIN,

    @SerialName("draw")
    DRAW,

    @SerialName("blessed-loss")
    BLESSED_LOSS,

    @SerialName("maybe-loss")
    MAYBE_LOSS,

    @SerialName("syzygy-loss")
    SYZYGY_LOSS,

    @SerialName("loss")
    LOSS
}

@Serializable
data class TablebaseJson(
    val category: TablebaseJsonCategory,
    val dtz: Int? = null,
    @SerialName("precise_dtz")
    val preciseDtz: Int? = null,
    val dtc: Int? = null,
    val dtm: Int? = null,
    val dtw: Int? = null,
    val checkmate: Boolean? = null,
    val stalemate: Boolean? = null,
    @SerialName("variant_win")
    val variantWin: Boolean? = null,
    @SerialName("variant_loss")
    val variantLoss: Boolean? = null,
    @SerialName("insufficient_material")
    val insufficientMaterial: Boolean? = null,
    val moves: List<TablebaseMove>
)
