package com.viplearner.chess

import com.viplearner.model.formatted
import kotlin.jvm.JvmRecord

interface Square<T : PieceType> {
    data class Empty<T : PieceType>(val pos: Pos) : Square<T> {
        override fun pos(): Pos {
            return pos
        }
    }

    class With<T : PieceType>(val pos: Pos, val type: T, val side: Side) : Square<T> {
        override fun pos(): Pos {
            return pos
        }
    }

    interface Pos {
        fun file(): Char
        fun rank(): Int
        fun withFile(newFile: Char): Pos {
            return com.viplearner.chess.Square.Companion.pos(newFile, rank())
        }

        fun withRank(newRank: Int): Pos {
            return com.viplearner.chess.Square.Companion.pos(file(), newRank)
        }

        fun delta(deltaRank: Int, deltaFile: Int): Pos {
            return withRank(rank() + deltaRank)
                .withFile((file().code + deltaFile).toChar())
        }
    }

    fun pos(): Pos
    fun file(): Char {
        return pos().file()
    }

    fun rank(): Int {
        return pos().rank()
    }

    data class FileRank(val file: Char, val rank: Int) : Pos {
        override fun toString(): String {
            return "%s%s".formatted(file, rank)
        }

        override fun file(): Char {
            return file
        }

        override fun rank(): Int {
            return rank
        }
    }

    companion object {
        fun <T : PieceType> empty(file: Char, rank: Int): Empty<T> {
            return empty(
                pos(
                    file,
                    rank
                )
            )
        }

        fun <T : PieceType> empty(square: Square<T>): Empty<T> {
            return com.viplearner.chess.Square.Empty<T>(square.pos())
        }

        fun <T : PieceType> empty(pos: Pos): Empty<T> {
            return com.viplearner.chess.Square.Empty<T>(pos)
        }

        fun <T : PieceType> withPiece(file: Char, rank: Int, type: T, side: Side): With<T> {
            return withPiece(com.viplearner.chess.Square.Companion.pos(file, rank), type, side)
        }

        fun <T : PieceType> withPiece(square: Square<T>, type: T, side: Side): With<T> {
            return withPiece(square.pos(), type, side)
        }

        fun <T : PieceType> withPiece(pos: Pos, type: T, side: Side): With<T> {
            return com.viplearner.chess.Square.With(pos, type, side)
        }

        fun pos(fileRank: String): Pos {
            return com.viplearner.chess.Square.Companion.pos(fileRank[0], fileRank[1].digitToInt())
        }

        fun pos(file: Char, rank: Int): Pos {
            return com.viplearner.chess.Square.FileRank(file, rank)
        }

        val a1: Pos = com.viplearner.chess.Square.FileRank('a', 1)
        val a2: Pos = com.viplearner.chess.Square.FileRank('a', 2)
        val a3: Pos = com.viplearner.chess.Square.FileRank('a', 3)
        val a4: Pos = com.viplearner.chess.Square.FileRank('a', 4)
        val a5: Pos = com.viplearner.chess.Square.FileRank('a', 5)
        val a6: Pos = com.viplearner.chess.Square.FileRank('a', 6)
        val a7: Pos = com.viplearner.chess.Square.FileRank('a', 7)
        val a8: Pos = com.viplearner.chess.Square.FileRank('a', 8)

        val b1: Pos = com.viplearner.chess.Square.FileRank('b', 1)
        val b2: Pos = com.viplearner.chess.Square.FileRank('b', 2)
        val b3: Pos = com.viplearner.chess.Square.FileRank('b', 3)
        val b4: Pos = com.viplearner.chess.Square.FileRank('b', 4)
        val b5: Pos = com.viplearner.chess.Square.FileRank('b', 5)
        val b6: Pos = com.viplearner.chess.Square.FileRank('b', 6)
        val b7: Pos = com.viplearner.chess.Square.FileRank('b', 7)
        val b8: Pos = com.viplearner.chess.Square.FileRank('b', 8)

        val c1: Pos = com.viplearner.chess.Square.FileRank('c', 1)
        val c2: Pos = com.viplearner.chess.Square.FileRank('c', 2)
        val c3: Pos = com.viplearner.chess.Square.FileRank('c', 3)
        val c4: Pos = com.viplearner.chess.Square.FileRank('c', 4)
        val c5: Pos = com.viplearner.chess.Square.FileRank('c', 5)
        val c6: Pos = com.viplearner.chess.Square.FileRank('c', 6)
        val c7: Pos = com.viplearner.chess.Square.FileRank('c', 7)
        val c8: Pos = com.viplearner.chess.Square.FileRank('c', 8)

        val d1: Pos = com.viplearner.chess.Square.FileRank('d', 1)
        val d2: Pos = com.viplearner.chess.Square.FileRank('d', 2)
        val d3: Pos = com.viplearner.chess.Square.FileRank('d', 3)
        val d4: Pos = com.viplearner.chess.Square.FileRank('d', 4)
        val d5: Pos = com.viplearner.chess.Square.FileRank('d', 5)
        val d6: Pos = com.viplearner.chess.Square.FileRank('d', 6)
        val d7: Pos = com.viplearner.chess.Square.FileRank('d', 7)
        val d8: Pos = com.viplearner.chess.Square.FileRank('d', 8)

        val e1: Pos = com.viplearner.chess.Square.FileRank('e', 1)
        val e2: Pos = com.viplearner.chess.Square.FileRank('e', 2)
        val e3: Pos = com.viplearner.chess.Square.FileRank('e', 3)
        val e4: Pos = com.viplearner.chess.Square.FileRank('e', 4)
        val e5: Pos = com.viplearner.chess.Square.FileRank('e', 5)
        val e6: Pos = com.viplearner.chess.Square.FileRank('e', 6)
        val e7: Pos = com.viplearner.chess.Square.FileRank('e', 7)
        val e8: Pos = com.viplearner.chess.Square.FileRank('e', 8)

        val f1: Pos = com.viplearner.chess.Square.FileRank('f', 1)
        val f2: Pos = com.viplearner.chess.Square.FileRank('f', 2)
        val f3: Pos = com.viplearner.chess.Square.FileRank('f', 3)
        val f4: Pos = com.viplearner.chess.Square.FileRank('f', 4)
        val f5: Pos = com.viplearner.chess.Square.FileRank('f', 5)
        val f6: Pos = com.viplearner.chess.Square.FileRank('f', 6)
        val f7: Pos = com.viplearner.chess.Square.FileRank('f', 7)
        val f8: Pos = com.viplearner.chess.Square.FileRank('f', 8)

        val g1: Pos = com.viplearner.chess.Square.FileRank('g', 1)
        val g2: Pos = com.viplearner.chess.Square.FileRank('g', 2)
        val g3: Pos = com.viplearner.chess.Square.FileRank('g', 3)
        val g4: Pos = com.viplearner.chess.Square.FileRank('g', 4)
        val g5: Pos = com.viplearner.chess.Square.FileRank('g', 5)
        val g6: Pos = com.viplearner.chess.Square.FileRank('g', 6)
        val g7: Pos = com.viplearner.chess.Square.FileRank('g', 7)
        val g8: Pos = com.viplearner.chess.Square.FileRank('g', 8)

        val h1: Pos = com.viplearner.chess.Square.FileRank('h', 1)
        val h2: Pos = com.viplearner.chess.Square.FileRank('h', 2)
        val h3: Pos = com.viplearner.chess.Square.FileRank('h', 3)
        val h4: Pos = com.viplearner.chess.Square.FileRank('h', 4)
        val h5: Pos = com.viplearner.chess.Square.FileRank('h', 5)
        val h6: Pos = com.viplearner.chess.Square.FileRank('h', 6)
        val h7: Pos = com.viplearner.chess.Square.FileRank('h', 7)
        val h8: Pos = com.viplearner.chess.Square.FileRank('h', 8)
    }
}
