package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class TablebaseMoveCategory {
    @SerialName("loss")
    LOSS,

    @SerialName("unknown")
    UNKNOWN,

    @SerialName("syzygy-loss")
    SYZYGY_LOSS,

    @SerialName("maybe-loss")
    MAYBE_LOSS,

    @SerialName("blessed-loss")
    BLESSED_LOSS,

    @SerialName("draw")
    DRAW,

    @SerialName("cursed-win")
    CURSED_WIN,

    @SerialName("maybe-win")
    MAYBE_WIN,

    @SerialName("syzygy-win")
    SYZYGY_WIN,

    @SerialName("win")
    WIN
}

@Serializable
data class TablebaseMove(
    val uci: String,
    val san: String,
    val category: TablebaseMoveCategory,
    val dtz: Int? = null,
    @SerialName("precise_dtz")
    val preciseDtz: Int? = null,
    val dtc: Int? = null,
    val dtm: Int? = null,
    val dtw: Int? = null,
    val zeroing: Boolean? = null,
    val conversion: Boolean? = null,
    val checkmate: Boolean? = null,
    val stalemate: Boolean? = null,
    @SerialName("variant_win")
    val variantWin: Boolean? = null,
    @SerialName("variant_loss")
    val variantLoss: Boolean? = null,
    @SerialName("insufficient_material")
    val insufficientMaterial: Boolean? = null
)
