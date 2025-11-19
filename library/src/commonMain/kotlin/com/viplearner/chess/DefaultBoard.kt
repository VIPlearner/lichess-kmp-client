package com.viplearner.chess

import com.viplearner.model.*
import com.viplearner.chess.Square.*
import chariot.internal.chess.InternalDefaultBoard
import kotlin.jvm.JvmRecord

interface DefaultBoard : PieceTypedBoard<Piece> {
    fun play(vararg uciOrSan: String): DefaultBoard

    fun play(from: Pos, to: Pos): DefaultBoard? {
        return play(from.toString() + to.toString())
    }

    fun play(from: Pos, to: Pos, promotion: Piece): DefaultBoard? {
        return play(from.toString() + to.toString() + promotion.toChar())
    }

    fun play(from: Square<Piece>, to: Square<Piece>): DefaultBoard? {
        return play(from.pos(), to.pos())
    }

    fun play(from: Square<Piece>, to: Square<Piece>, promotion: Piece): DefaultBoard? {
        return play(from.pos(), to.pos(), promotion)
    }

    fun toSAN(move: String): String?
    fun toUCI(move: String): String?
    fun toFEN(): String?
    fun variant(): String?
    fun validMoves(): Collection<String?>?
    fun sideToMove(): Side

    fun historyFEN(): List<String?>?
    fun historyMove(): List<String?>?

    fun whiteToMove(): Boolean {
        return sideToMove() === Side.white
    }

    fun blackToMove(): Boolean {
        return !whiteToMove()
    }

    interface Config {
        fun frame(frame: Boolean = true): Config?

        fun letter(letter: Boolean = true): Config?

        fun coordinates(coordinates: Boolean = true): Config?

        fun flipped(flipped: Boolean = true): Config?
    }

    @JvmRecord
    data class Data(val frame: Boolean, val letter: Boolean, val coordinates: Boolean, val flipped: Boolean) {
        interface Component

        @JvmRecord
        internal data class Frame(val value: Boolean) : Component

        @JvmRecord
        internal data class Letter(val value: Boolean) : Component

        @JvmRecord
        internal data class Coordinates(val value: Boolean) : Component

        @JvmRecord
        internal data class Flipped(val value: Boolean) : Component

        fun with(component: Component?): Data {
            return Data(
                if (component is Frame) component.value else frame,
                if (component is Letter) component.value else letter,
                if (component is Coordinates) component.value else coordinates,
                if (component is Flipped) component.value else flipped
            )
        }

        fun with(vararg components: Component?): Data {
            var copy = this
            for (component in components) copy = copy.with(component)
            return copy
        }
    }

    companion object {
        fun ofStandard(): DefaultBoard {
            return of(Board.ofStandard())
        }

        fun of(board: Board): DefaultBoard {
            return InternalDefaultBoard.of(board)
        }

        fun fromFEN(fen: String): DefaultBoard {
            return of(Board.fromFEN(fen))
        }


        fun fenPositionsToSquares(positionsOrFen: String): Map<Pos, Square<Piece>> {
            // in case complete FEN, just keep positions part
            val positions = positionsOrFen.trim().split(" ")[0]

            val squareList: MutableList<Square<Piece>> = mutableListOf()
            val ranks: List<String> = positions.split("/")
            for (rank in ranks.indices.reversed()) {
                var file = 0
                for (c in ranks[rank].toCharArray()) {
                    if (c >= '1' && c <= '8') {
                        val numEmptySquares: Int = c.digitToInt()
                        for (i in 0..<numEmptySquares) {
                            squareList.add(Square.empty(('a'.code + i + file).toChar(), 8 - rank))
                        }
                        file += numEmptySquares
                    } else {
                        val pieceAndSide: Piece.PieceAndSide? = Piece.fromCharWithSide(c)
                        if (pieceAndSide != null) {
                            squareList.add(Square.withPiece(
                                ('a'.code + file).toChar(),
                                8 - rank,
                                pieceAndSide.piece,
                                pieceAndSide.side
                            ))
                        }
                            file++
                    }
                }
            }

            val squareMap: Map<Pos, Square<Piece>> = squareList
                .sortedWith(compareBy<Square<Piece>> { it.pos().file() }
                    .thenBy { it.pos().rank() })
                .associateByTo(LinkedHashMap()) { it.pos() }

            return squareMap
        }

        fun squaresToFenPositions(squareMap: Map<Pos, Square<Piece>>): String {
            val rows: MutableList<String> = mutableListOf()
            for (rank in 7 downTo 0) {
                var line = ""
                var empty = 0
                for (file in 0..7) {
                    val square = squareMap[Square.pos(('a'.code + file).toChar(), rank + 1)]
                    if (square !is With<*>) {
                        empty++
                        continue
                    }
                    if (empty > 0) {
                        line += empty.toString()
                        empty = 0
                    }
                    val piece = square.type as Piece
                    val side = square.side
                    line += piece.toChar(side)
                }
                if (empty > 0) {
                    line += empty.toString()
                }
                rows.add(line)
            }
            return rows.joinToString("/")
        }

        val noframeTemplate: String = """
        %s %s %s %s %s %s %s %s
        %s %s %s %s %s %s %s %s
        %s %s %s %s %s %s %s %s
        %s %s %s %s %s %s %s %s
        %s %s %s %s %s %s %s %s
        %s %s %s %s %s %s %s %s
        %s %s %s %s %s %s %s %s
        %s %s %s %s %s %s %s %s
        """.trimIndent()

        val frameTemplate: String = """
        ┌───┬───┬───┬───┬───┬───┬───┬───┐
        │ %s│ %s│ %s│ %s│ %s│ %s│ %s│ %s│
        ├───┼───┼───┼───┼───┼───┼───┼───┤
        │ %s│ %s│ %s│ %s│ %s│ %s│ %s│ %s│
        ├───┼───┼───┼───┼───┼───┼───┼───┤
        │ %s│ %s│ %s│ %s│ %s│ %s│ %s│ %s│
        ├───┼───┼───┼───┼───┼───┼───┼───┤
        │ %s│ %s│ %s│ %s│ %s│ %s│ %s│ %s│
        ├───┼───┼───┼───┼───┼───┼───┼───┤
        │ %s│ %s│ %s│ %s│ %s│ %s│ %s│ %s│
        ├───┼───┼───┼───┼───┼───┼───┼───┤
        │ %s│ %s│ %s│ %s│ %s│ %s│ %s│ %s│
        ├───┼───┼───┼───┼───┼───┼───┼───┤
        │ %s│ %s│ %s│ %s│ %s│ %s│ %s│ %s│
        ├───┼───┼───┼───┼───┼───┼───┼───┤
        │ %s│ %s│ %s│ %s│ %s│ %s│ %s│ %s│
        └───┴───┴───┴───┴───┴───┴───┴───┘
        """.trimIndent()

        fun render(board: Board): String {
            return render(board) { }
        }

        fun render(board: Board, config: (Config) -> Unit): String {
            val toConsume = object : Config {
                var mutate: Data = Data(false, false, false, false)

                override fun frame(frame: Boolean): Config {
                    mutate = mutate.with(Data.Frame(frame))
                    return this
                }

                override fun letter(letter: Boolean): Config {
                    mutate = mutate.with(Data.Letter(letter))
                    return this
                }

                override fun coordinates(coordinates: Boolean): Config {
                    mutate = mutate.with(Data.Coordinates(coordinates))
                    return this
                }

                override fun flipped(flipped: Boolean): Config {
                    mutate = mutate.with(Data.Flipped(flipped))
                    return this
                }
            }
            config(toConsume)
            val data: Data = toConsume.mutate
            return render(board, data)
        }

        fun toLetter(square: With<Piece>): String {
            return String.valueOf(square.type.toChar(square.side))
        }

        fun toUnicode(square: With<Piece>): String {
            return when (square.type) {
                Piece.pawn -> if (square.side === Side.black) "♟" else "♙"
                Piece.knight -> if (square.side === Side.black) "♞" else "♘"
                Piece.bishop -> if (square.side === Side.black) "♝" else "♗"
                Piece.rook -> if (square.side === Side.black) "♜" else "♖"
                Piece.queen -> if (square.side === Side.black) "♛" else "♕"
                Piece.king -> if (square.side === Side.black) "♚" else "♔"
            }
        }

        private fun render(board: Board, config: Data): String {
            val render = { p: With<Piece> ->
                (if (config.letter)
                    toLetter(p)
                else
                    toUnicode(p)
                        )
                    .toString() + (if (config.frame) " " else "")
            }

            val empty =  { if (config.frame) "  " else " " }
            var template: String =
                if (config.frame) frameTemplate else noframeTemplate

            if (config.coordinates) {
                val withoutCoordinates = template.lines().toList()
                val withCoordinates = ArrayList<String?>()

                val rankComparator =
                    Comparator<String> { a, b -> if (config.flipped) a.compareTo(b) else b.compareTo(a) }

                val fileComparator =
                    Comparator<String> { a, b -> if (config.flipped) b.compareTo(a) else a.compareTo(b) }

                val ranks = "12345678".map { it.toString() }.sortedWith(rankComparator).iterator()
                val files = listOf(" ") + "abcdefgh".map { it.toString() }.sortedWith(fileComparator)

                for (line in 0..withoutCoordinates.size) {
                    val renderRank = (!config.frame) || line % 2 != 0
                    val prefix: String? = if (renderRank) ranks.next() else " "
                    withCoordinates.add(prefix.toString() + " " + withoutCoordinates.get(line) + "\n")
                }
                withCoordinates.add(String.join("%1\$s", files).formatted(if (config.frame) "   " else " "))
                template = withCoordinates.joinToString()
            }
            val standardBoard: DefaultBoard = of(board)

            val map: Map<Pos, Square<Piece>> =
                standardBoard.squares().all().associateBy { it.pos() }

            val pieces = ArrayList<String?>()
            if (!config.flipped) {
                for (row in 7 downTo 0) for (col in 0..7) {
                    val square = map.get(
                        Square.pos(
                            ('a'.code + col).toChar(),
                            row + 1,
                        ),
                    )
                    pieces.add(
                        if (square is With<Piece>
                        ) (render(square)) else empty.invoke(),
                    )
                }
            } else {
                for (row in 0..7) for (col in 7 downTo 0) {
                    val square = map.get(
                        Square.pos(
                            ('a'.code + col).toChar(),
                            row + 1,
                        ),
                    )
                    pieces.add(
                        if (square is With<Piece>
                        ) (render(square)) else empty(),
                    )
                }
            }
            return template.formatted(*(pieces.toTypedArray()))
        }
    }
}
