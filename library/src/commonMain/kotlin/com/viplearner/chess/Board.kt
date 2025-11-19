package com.viplearner.chess

import com.viplearner.model.Optional
import com.viplearner.model.formatted
import com.viplearner.model.join

interface Board {
    fun play(move: Move): Board

    fun toSAN(move: Move): String
    fun toUCI(move: Move): String
    fun toFEN(): String
    fun validMoves(): Collection<String>
    fun variant(): String

    fun asMoves(vararg moves: String): List<Move> {
        return moves.flatMap { s -> s.split(" ") }
            .filter { s -> s.isNotEmpty() }
            .map(Move.Companion::wrap)
    }

    fun sideToMove(): Side {
        return if (toFEN().contains(" w ")) Side.white else Side.black
    }

    fun moveNum(): Int {
        return FEN.Companion.parse(toFEN()).move()
    }

    fun play(vararg moves: String): Board {
        return asMoves(*moves).fold(this) {
                                          board, move -> board.play(move)
        }
    }

    fun toFEN(vararg moves: String): String {
        return play(*moves).toFEN()
    }

    fun toSAN(vararg moves: String): String {
        val list: List<Move> = asMoves(*moves)
        if (list.isEmpty()) return ""
        return list.drop(1).fold(toSAN(list.first())) { sans, next ->
            String.join(" ", sans, play(sans).toSAN(next))
        }
    }

    fun toUCI(vararg moves: String): String {
        val list: List<Move> = asMoves(*moves)
        if (list.isEmpty()) return ""
        return list.drop(1).fold(toUCI(list.first())) { ucis, next ->
            String.join(" ", ucis, play(ucis).toUCI(next))
        }
    }

    fun toPGN(vararg moves: String): String? {
        val end = play(*moves)
        val sans: List<String> = toSAN(*moves).split(" ")
            .filter { s -> s.isNotEmpty() }

        val currentFEN: FEN = FEN.Companion.parse(toFEN())
        val endFEN: FEN = FEN.Companion.parse(end.toFEN())

        val lastMove = if (sans.isEmpty()) "" else sans.last()

        val result: String? = when (end.validMoves().isEmpty()) {
            true -> when {
                lastMove.contains("#") -> when (endFEN.side()) {
                    Side.white -> "0-1"
                    Side.black -> "1-0"
                }
                else -> "1/2-1/2"
            }
            false -> "*"
        }

        if (sans.isEmpty()) return result

        val sb = StringBuilder()
        var move: Int = currentFEN.move()

        if (currentFEN.side() === Side.black) {
            sb.append("%d... %s ".formatted(move, sans.first()))
            move++
        }
        var i = if (currentFEN.side() === Side.black) 1 else 0
        while (i < sans.size) {
            if (i + 1 < sans.size) sb.append("%d. %s %s ".formatted(move, sans.get(i), sans.get(i + 1)))
            else sb.append("%d. %s ".formatted(move, sans.get(i)))
            move++
            i += 2
        }
        return sb.toString() + result
    }

    companion object {
        fun ofStandard(): Board {
            return BoardProvider.Companion.providers().getOrElse("standard", { InternalBoardProvider.provider() })
                .init("standard")
        }

        fun ofStandard(fen: String): Board {
            return ofVariantAndFEN("standard", fen)
                .orElseGet({ InternalBoardProvider.provider().fromFEN("standard", fen) ?: throw IllegalArgumentException("Invalid FEN: $fen") })
        }

        fun ofChess960(position: Int): Board {
            val provider: BoardProvider? = BoardProvider.Companion.providers().get("chess960")
            if (provider is Chess960BoardProvider) {
                return provider.fromPosition(position)
            }
            if (provider != null) {
                return provider.fromFEN("chess960", InternalBoardProvider.provider().positionToFEN(position)) ?: throw IllegalArgumentException("Invalid Chess960 position: $position")
            }
            return InternalBoardProvider.provider().fromPosition(position)
        }

        fun ofChess960(fen: String): Board {
            return ofVariantAndFEN("chess960", fen)
                .orElseGet({ InternalBoardProvider.provider().fromFEN("chess960", fen) })
        }

        fun ofVariantAndFEN(variant: String, fen: String): Optional<Board> {
            return Optional.ofNullable(BoardProvider.Companion.providers().get(variant))
                .map({ provider -> provider.fromFEN(variant, fen) })
        }

        fun fromFEN(fen: String): Board {
            return ofStandard(fen)
        }
    }
}
