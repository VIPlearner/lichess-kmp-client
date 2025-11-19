package chariot.internal.chess

import com.viplearner.chess.Board
import com.viplearner.chess.DefaultBoard
import com.viplearner.chess.Piece
import com.viplearner.chess.PieceTypedBoard
import com.viplearner.chess.PieceTypedBoard.Pieces
import com.viplearner.chess.PieceTypedBoard.Squares
import com.viplearner.chess.Side
import com.viplearner.chess.Square
import com.viplearner.chess.Square.Pos
import kotlin.jvm.JvmRecord

/**
 * DefaultStandardBoard is the internal implementation of StandardBoard.
 * StandardBoard exists to provide a user-friendly typed API which allows for
 * making moves and querying about pieces.
 * StandardBoard wraps a `Board delegate`,
 * allowing for the chess logic to come from user-provided code via BoardProvider SPI.
 * To begin with, a NaiveChess implementation will be provided by chariot.
 *
 * NaiveChess will internally use the same piece representation
 * that StandardBoard provides in its typed API (a non-naive solution would be
 * using bitboards).
 *
 * So the delegate knows the current FEN and how FEN is modified given a move.
 * StandardBoard knows how to translate FEN to typed API and from typed API to FEN.
 */
class InternalDefaultBoard(
    val delegate: Board,
    val captures: List<Piece.PieceAndSide>,
    val initialFEN: String?,
    val history: List<MoveAndFen>
) : DefaultBoard {
    @JvmRecord
    data class MoveAndFen(val move: String, val fen: String)

    override fun play(vararg uciOrSan: String): DefaultBoard {
        val moves = this.delegate.asMoves(*uciOrSan)
        var board = this
        for (move in moves) {
            val uci = board.toUCI(move.asString())
            board = board.playUCI(uci)
        }
        return board
    }

    fun playUCI(uci: String): InternalDefaultBoard {
        val next: Board = delegate.play(uci)
        if (delegate.toFEN().equals(next.toFEN())) return this

        var withCaptures: List<Piece.PieceAndSide> = this.captures
        val to: Pos = Square.pos(uci.substring(2, 4))
        val board = DefaultBoard.fenPositionsToSquares(delegate.toFEN())[to] ?: throw IllegalStateException("No piece at destination square $to")
        if (board is Square.With) {
            withCaptures = withCaptures + board.type.withSide(board.side)
        }

        val withHistory = this.history + MoveAndFen(uci, next.toFEN())
        return InternalDefaultBoard(next, withCaptures, this.initialFEN, withHistory)
    }

    // delegate...
    override fun toSAN(move: String): String {
        return delegate.toSAN(move)
    }

    override fun toUCI(move: String): String {
        return delegate.toUCI(move)
    }

    override fun toFEN(): String {
        return delegate.toFEN()
    }

    override fun variant(): String {
        return delegate.variant()
    }

    override fun validMoves(): Collection<String?> {
        return delegate.validMoves()
    }

    override fun sideToMove(): Side {
        return delegate.sideToMove()
    }


    // ...delegate
    override fun historyFEN(): List<String?> {
        return listOfNotNull(this.initialFEN) + this.history.map { it.fen }
    }

    override fun historyMove(): List<String> {
        return this.history.map { it.move }
    }

    override fun squares(): Squares<Piece> {
        val squares: Map<Pos, Square<Piece>> = DefaultBoard.fenPositionsToSquares(delegate.toFEN())
        return object : Squares<Piece> {
            override fun get(pos: Pos): Square<Piece> {
                return squares.get(pos) ?: throw IllegalArgumentException("Invalid position: $pos")
            }

            override fun all(): List<Square<Piece>> {
                return squares.values.toList()
            }
        }
    }

    override fun pieces(): Pieces<Piece> {
        val squares: Map<Pos, Square<Piece>> = DefaultBoard.fenPositionsToSquares(delegate.toFEN())
        return object : Pieces<Piece> {
            override fun all(): List<Square.With<Piece>> {
                return squares.values.filterIsInstance<Square.With<Piece>>()
            }

            override fun all(side: Side): List<Square.With<Piece>> {
                return all().filter { it.side === side }
            }

            override fun of(type: Piece): List<Square.With<Piece>> {
                return all().filter { it.type === type }
            }

            override fun of(type: Piece, side: Side): List<Square.With<Piece>> {
                return of(type).filter { it.side === side }
            }

            override fun captured(side: Side): List<Piece> {
                return this@InternalDefaultBoard.captures
                    .filter { it.side === side }
                    .map { it.piece }
            }
        }
    }

    companion object {
        fun of(board: Board): InternalDefaultBoard {
            return InternalDefaultBoard(board, listOf(), board.toFEN(), listOf())
        }
    }
}
