package com.viplearner.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    LOSS,
}

@Serializable
data class TablebaseJson(
    val category: TablebaseJsonCategory,
    val dtz: Long? = null,
    @SerialName("precise_dtz")
    val preciseDtz: Long? = null,
    val dtc: Long? = null,
    val dtm: Long? = null,
    val dtw: Long? = null,
    val checkmate: Boolean? = null,
    val stalemate: Boolean? = null,
    @SerialName("variant_win")
    val variantWin: Boolean? = null,
    @SerialName("variant_loss")
    val variantLoss: Boolean? = null,
    @SerialName("insufficient_material")
    val insufficientMaterial: Boolean? = null,
    val moves: List<TablebaseMove>,
)
