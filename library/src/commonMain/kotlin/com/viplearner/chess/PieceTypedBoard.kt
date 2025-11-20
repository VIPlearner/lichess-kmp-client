package com.viplearner.chess

import com.viplearner.chess.Square.Pos

interface PieceTypedBoard<T : PieceType> {
    fun pieces(): Pieces<T>

    fun squares(): Squares<T>

    fun get(pos: Pos): Square<T>? {
        return squares().get(pos)
    }

    interface Squares<T : PieceType> {
        fun all(): List<Square<T>>

        fun get(pos: Pos): Square<T>
    }

    interface Pieces<T : PieceType> {
        fun all(): List<Square.With<T>>

        fun all(side: Side): List<Square.With<T>>

        fun of(type: T): List<Square.With<T>>

        fun of(
            type: T,
            side: Side,
        ): List<Square.With<T>>

        fun captured(side: Side): List<T>
    }
}
