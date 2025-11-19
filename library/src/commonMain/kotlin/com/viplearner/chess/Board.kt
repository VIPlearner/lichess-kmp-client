package com.viplearner.chess

import chariot.internal.chess.InternalBoardProvider
import com.viplearner.model.Optional
import com.viplearner.model.findFirst
import com.viplearner.model.formatted
import com.viplearner.model.gather
import com.viplearner.model.getFirst
import com.viplearner.model.getLast
import com.viplearner.model.join
import com.viplearner.model.skip
import com.viplearner.model.stream
import com.viplearner.model.stream as kstream

interface Board {
    fun play(move: Move): Board

    fun toSAN(move: Move): String
    fun toUCI(move: Move): String
    fun toFEN(): String
    fun validMoves(): Collection<String>
    fun variant(): String

    fun asMoves(vararg moves: String): List<Move> {
        return Arrays.stream(moves)
            .flatMap({ s -> Arrays.stream(s.split(" ")) })
            .filter({ s -> !s.isEmpty() })
            .map(Move.Companion::wrap)
            .toList()
    }

    fun sideToMove(): Side {
        return if (toFEN().contains(" w ")) Side.white else Side.black
    }

    fun moveNum(): Int {
        return FEN.Companion.parse(toFEN()).move()
    }

    fun play(vararg moves: String): Board {
        return asMoves(*moves).kstream()
            .gather(Gatherers.fold({ this }, { board, move -> board.play(move) }))
            .findFirst().orElse(this)!!
    }

    fun toFEN(vararg moves: String): String {
        return play(*moves).toFEN()
    }

    fun toSAN(vararg moves: String): String {
        val list: List<Move> = asMoves(*moves)
        if (list.isEmpty()) return ""
        return list.kstream().skip(1)
            .gather(
                Gatherers.fold(
                    { toSAN(list.getFirst()!!) },
                    { sans, next -> String.join(" ", sans, play(sans)!!.toSAN(next)) })
            )
            .findFirst().orElse("")!!
    }

    fun toUCI(vararg moves: String): String {
        val list: List<Move> = asMoves(*moves)
        if (list.isEmpty()) return ""
        return list.kstream().skip(1)
            .gather(
                Gatherers.fold(
                    { toUCI(list.getFirst()!!) },
                    { ucis, next -> String.join(" ", ucis, play(ucis)!!.toUCI(next)) })
            )
            .findFirst().orElse("")!!
    }

    fun toPGN(vararg moves: String): String? {
        val end = play(*moves)
        val sans: List<String?> = Arrays.stream(toSAN(*moves)!!.split(" "))
            .filter({ s -> !s.isEmpty() })
            .toList()

        val currentFEN: FEN = FEN.Companion.parse(toFEN())
        val endFEN: FEN = FEN.Companion.parse(end.toFEN())

        val lastMove = if (sans.isEmpty()) "" else sans.getLast()!!

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
            sb.append("%d... %s ".formatted(move, sans.getFirst()))
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
