package com.viplearner.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class UserPreferencesTheme {
    @SerialName("blue")
    BLUE,

    @SerialName("blue2")
    BLUE2,

    @SerialName("blue3")
    BLUE3,

    @SerialName("blue-marble")
    BLUE_MARBLE,

    @SerialName("canvas")
    CANVAS,

    @SerialName("wood")
    WOOD,

    @SerialName("wood2")
    WOOD2,

    @SerialName("wood3")
    WOOD3,

    @SerialName("wood4")
    WOOD4,

    @SerialName("maple")
    MAPLE,

    @SerialName("maple2")
    MAPLE2,

    @SerialName("brown")
    BROWN,

    @SerialName("leather")
    LEATHER,

    @SerialName("green")
    GREEN,

    @SerialName("marble")
    MARBLE,

    @SerialName("green-plastic")
    GREEN_PLASTIC,

    @SerialName("grey")
    GREY,

    @SerialName("metal")
    METAL,

    @SerialName("olive")
    OLIVE,

    @SerialName("newspaper")
    NEWSPAPER,

    @SerialName("purple")
    PURPLE,

    @SerialName("purple-diag")
    PURPLE_DIAG,

    @SerialName("pink")
    PINK,

    @SerialName("ic")
    IC
}

@Serializable
enum class UserPreferencesPieceset {
    @SerialName("cburnett")
    CBURNETT,

    @SerialName("merida")
    MERIDA,

    @SerialName("alpha")
    ALPHA,

    @SerialName("pirouetti")
    PIROUETTI,

    @SerialName("chessnut")
    CHESSNUT,

    @SerialName("chess7")
    CHESS7,

    @SerialName("reillycraig")
    REILLYCRAIG,

    @SerialName("companion")
    COMPANION,

    @SerialName("riohacha")
    RIOHACHA,

    @SerialName("kosal")
    KOSAL,

    @SerialName("leipzig")
    LEIPZIG,

    @SerialName("fantasy")
    FANTASY,

    @SerialName("spatial")
    SPATIAL,

    @SerialName("california")
    CALIFORNIA,

    @SerialName("pixel")
    PIXEL,

    @SerialName("maestro")
    MAESTRO,

    @SerialName("fresca")
    FRESCA,

    @SerialName("cardinal")
    CARDINAL,

    @SerialName("gioco")
    GIOCO,

    @SerialName("tatiana")
    TATIANA,

    @SerialName("staunty")
    STAUNTY,

    @SerialName("governor")
    GOVERNOR,

    @SerialName("dubrovny")
    DUBROVNY,

    @SerialName("icpieces")
    ICPIECES,

    @SerialName("shapes")
    SHAPES,

    @SerialName("letter")
    LETTER
}

@Serializable
enum class UserPreferencesTheme3d {
    @SerialName("Black-White-Aluminium")
    BLACK_WHITE_ALUMINIUM,

    @SerialName("Brushed-Aluminium")
    BRUSHED_ALUMINIUM,

    @SerialName("China-Blue")
    CHINA_BLUE,

    @SerialName("China-Green")
    CHINA_GREEN,

    @SerialName("China-Grey")
    CHINA_GREY,

    @SerialName("China-Scarlet")
    CHINA_SCARLET,

    @SerialName("Classic-Blue")
    CLASSIC_BLUE,

    @SerialName("Gold-Silver")
    GOLD_SILVER,

    @SerialName("Light-Wood")
    LIGHT_WOOD,

    @SerialName("Power-Coated")
    POWER_COATED,

    @SerialName("Rosewood")
    ROSEWOOD,

    @SerialName("Marble")
    MARBLE,

    @SerialName("Wax")
    WAX,

    @SerialName("Jade")
    JADE,

    @SerialName("Woodi")
    WOODI
}

@Serializable
enum class UserPreferencesPieceset3d {
    @SerialName("Basic")
    BASIC,

    @SerialName("Wood")
    WOOD,

    @SerialName("Metal")
    METAL,

    @SerialName("RedVBlue")
    REDVBLUE,

    @SerialName("ModernJade")
    MODERNJADE,

    @SerialName("ModernWood")
    MODERNWOOD,

    @SerialName("Glass")
    GLASS,

    @SerialName("Trimmed")
    TRIMMED,

    @SerialName("Experimental")
    EXPERIMENTAL,

    @SerialName("Staunton")
    STAUNTON,

    @SerialName("CubesAndPi")
    CUBESANDPI
}

@Serializable
enum class UserPreferencesSoundset {
    @SerialName("silent")
    SILENT,

    @SerialName("standard")
    STANDARD,

    @SerialName("piano")
    PIANO,

    @SerialName("nes")
    NES,

    @SerialName("sfx")
    SFX,

    @SerialName("futuristic")
    FUTURISTIC,

    @SerialName("robot")
    ROBOT,

    @SerialName("music")
    MUSIC,

    @SerialName("speech")
    SPEECH
}

@Serializable
data class UserPreferences(
    val dark: Boolean? = null,
    val transp: Boolean? = null,
    val bgImg: String? = null,
    val is3d: Boolean? = null,
    val theme: UserPreferencesTheme? = null,
    val pieceSet: UserPreferencesPieceset? = null,
    val theme3d: UserPreferencesTheme3d? = null,
    val pieceSet3d: UserPreferencesPieceset3d? = null,
    val soundSet: UserPreferencesSoundset? = null,
    val blindfold: Int? = null,
    val autoQueen: Int? = null,
    val autoThreefold: Int? = null,
    val takeback: Int? = null,
    val moretime: Int? = null,
    val clockTenths: Int? = null,
    val clockBar: Boolean? = null,
    val clockSound: Boolean? = null,
    val premove: Boolean? = null,
    val animation: Int? = null,
    val pieceNotation: Int? = null,
    val captured: Boolean? = null,
    val follow: Boolean? = null,
    val highlight: Boolean? = null,
    val destination: Boolean? = null,
    val coords: Int? = null,
    val replay: Int? = null,
    val challenge: Int? = null,
    val message: Int? = null,
    val submitMove: Int? = null,
    val confirmResign: Int? = null,
    val insightShare: Int? = null,
    val keyboardMove: Int? = null,
    val voiceMove: Boolean? = null,
    val zen: Int? = null,
    val ratings: Int? = null,
    val moveEvent: Int? = null,
    val rookCastle: Int? = null,
    val flairs: Boolean? = null,
    val sayGG: Int? = null
)
