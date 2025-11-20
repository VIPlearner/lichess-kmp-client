package com.viplearner.chess

import kotlin.jvm.JvmOverloads

enum class Piece : PieceType {
    pawn,
    knight,
    bishop,
    rook,
    queen,
    king,
    ;

    fun withSide(side: Side): PieceAndSide {
        return com.viplearner.chess.Piece.PieceAndSide(this, side)
    }

    @JvmOverloads
    fun toChar(side: Side? = Side.black): Char {
        val c =
            when (this) {
                com.viplearner.chess.Piece.pawn -> 'p'
                com.viplearner.chess.Piece.knight -> 'n'
                com.viplearner.chess.Piece.bishop -> 'b'
                com.viplearner.chess.Piece.rook -> 'r'
                com.viplearner.chess.Piece.queen -> 'q'
                com.viplearner.chess.Piece.king -> 'k'
            }
        return if (side === Side.black) {
            c
        } else {
            c.uppercaseChar()
        }
    }

    class PieceAndSide(val piece: Piece, val side: Side)

    companion object {
        fun fromChar(c: Char): Piece? {
            return when (c.lowercaseChar()) {
                'p' -> com.viplearner.chess.Piece.pawn
                'n' -> com.viplearner.chess.Piece.knight
                'b' -> com.viplearner.chess.Piece.bishop
                'r' -> com.viplearner.chess.Piece.rook
                'q' -> com.viplearner.chess.Piece.queen
                'k' -> com.viplearner.chess.Piece.king
                else -> null
            }
        }

        fun fromCharWithSide(c: Char): PieceAndSide? {
            val piece = fromChar(c)
            return if (piece is Piece) {
                piece.withSide(if (c.isLowerCase()) Side.black else Side.white)
            } else {
                null
            }
        }
    }
}
