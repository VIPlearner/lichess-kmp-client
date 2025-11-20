package com.viplearner.chess

import com.viplearner.model.formatted

interface FEN {
    fun positions(): String

    fun side(): Side

    fun castling(): String

    fun ep(): String

    fun halfMove(): Int

    fun move(): Int

    fun withPositions(positions: String): FEN

    fun withSide(side: Side): FEN

    fun withCastling(castling: String): FEN

    fun withEP(ep: String): FEN

    fun withHalfMove(halfMove: Int): FEN

    fun withMove(move: Int): FEN

    class Simple(val positions: String, val side: Side, val castling: String, val ep: String, val halfMove: Int, val move: Int) : FEN {
        override fun toString(): String {
            return "%s %s %s %s %d %d".formatted(
                positions,
                if (side === Side.black) 'b' else 'w',
                if (castling!!.isEmpty()) "-" else castling,
                ep,
                halfMove,
                move,
            )
        }

        override fun positions(): String {
            return positions
        }

        override fun side(): Side {
            return side
        }

        override fun castling(): String {
            return castling
        }

        override fun ep(): String {
            return ep
        }

        override fun halfMove(): Int {
            return halfMove
        }

        override fun move(): Int {
            return move
        }

        override fun withPositions(positions: String): Simple {
            return com.viplearner.chess.FEN.Simple(
                positions,
                this.side,
                this.castling,
                this.ep,
                this.halfMove,
                this.move,
            )
        }

        override fun withSide(side: Side): Simple {
            return com.viplearner.chess.FEN.Simple(
                this.positions,
                side,
                this.castling,
                this.ep,
                this.halfMove,
                this.move,
            )
        }

        override fun withCastling(castling: String): Simple {
            return com.viplearner.chess.FEN.Simple(
                this.positions,
                this.side,
                castling,
                this.ep,
                this.halfMove,
                this.move,
            )
        }

        override fun withEP(ep: String): Simple {
            return com.viplearner.chess.FEN.Simple(
                this.positions,
                this.side,
                this.castling,
                ep,
                this.halfMove,
                this.move,
            )
        }

        override fun withHalfMove(halfMove: Int): Simple {
            return com.viplearner.chess.FEN.Simple(
                this.positions,
                this.side,
                this.castling,
                this.ep,
                halfMove,
                this.move,
            )
        }

        override fun withMove(move: Int): Simple {
            return com.viplearner.chess.FEN.Simple(
                this.positions,
                this.side,
                this.castling,
                this.ep,
                this.halfMove,
                move,
            )
        }
    }

    companion object {
        val standardStr: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        val standard: FEN = com.viplearner.chess.FEN.Companion.parse(com.viplearner.chess.FEN.Companion.standardStr)

        fun parse(fen: String): FEN {
            val fields = fen.split(" ")
            val positions = if (fields.size > 0) fields[0] else ""
            val side = if (fields.size > 1) com.viplearner.chess.FEN.Companion.parseSide(fields[1]) else Side.white
            val castling = if (fields.size > 2) fields[2] else ""
            val ep = if (fields.size > 3) fields[3] else "-"
            val halfMove = if (fields.size > 4) com.viplearner.chess.FEN.Companion.parseInt(fields[4]) else 0
            val move = if (fields.size > 5) com.viplearner.chess.FEN.Companion.parseInt(fields[5]) else 1

            return com.viplearner.chess.FEN.Simple(positions, side, castling, ep, halfMove, move)
        }

        private fun parseSide(field: String?): Side {
            return if ("b".equals(field)) Side.black else Side.white
        }

        private fun parseInt(field: String): Int {
            try {
                return field.toInt()
            } catch (e: Exception) {
                return -1
            }
        }
    }
}
