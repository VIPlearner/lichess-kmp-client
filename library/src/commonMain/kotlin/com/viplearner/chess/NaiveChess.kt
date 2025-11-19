package com.viplearner.chess

import com.viplearner.chess.Square.Companion.withPiece
import com.viplearner.chess.Square.Pos
import com.viplearner.model.formatted
import com.viplearner.model.valueOf
import dev.simplx.Character
import kotlin.jvm.JvmRecord
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

// Naive implementation of chess (as opposed to efficient use of bitboard representations & operations)
class NaiveChess(val variant: String, val fen: FEN, val squareMap: Map<Pos, Square<Piece>>, val rookFiles: RookFiles) :
    Board {
    override fun validMoves(): Collection<String> {
        return piecesMatching { candidate -> candidate.side === fen.side() }
            .flatMap { piece -> validMovesByPiece(piece) }
            .map { move -> move.toUCI(variant()) }
            .toList()
    }

    override fun play(move: Move): Board {
        if (validMoves().isEmpty()) return this
        val internalMove = toInternalMove(move.asString())
        if (internalMove == null) return this
        return _play(internalMove)
    }

    override fun toFEN(): String {
        return this.fen.toString()
    }

    override fun variant(): String {
        return variant
    }

    override fun toUCI(move: Move): String {
        return anyToUCI(move.asString())
    }

    override fun toSAN(move: Move): String {
        val internalMove = toInternalMove(move.asString())

        val boardIfPlayed = _play(internalMove)

        val king = boardIfPlayed.piecesMatching { candidate ->
            candidate.side === this.fen.side().other() &&
                candidate.type === Piece.king
        }.firstOrNull() ?: throw IllegalStateException("No king found for side ${this.fen.side().other()}!")

        val inCheck: Boolean =
            boardIfPlayed.piecesMatching { candidate -> candidate.side === this.fen.side() }
                .any { candidate ->
                    boardIfPlayed.squaresAttackedByPiece(candidate)
                        .any { square -> square == king.pos() }
                }

        val checkSymbol = if (inCheck)
            (if (boardIfPlayed.validMoves().isEmpty())
                "#"
            else
                "+"
                    )
        else
            ""


        return when (internalMove) {
            is FromTo -> {
                val to = internalMove.to
                val from = internalMove.from
                val type = internalMove.from.type
                val side = internalMove.from.side

                val letter: String? = String.valueOf(type.toChar(Side.white))
                val capture = if (this.squareMap.get(to) is Square.With) "x" else ""


                when (internalMove.from.type) {
                    Piece.pawn -> if (this.squareMap.get(to) is Square.With || to.file() != from.file())
                        "%s".repeat(4).formatted(from.file(), capture, to, checkSymbol)
                    else
                        "%s".repeat(2).formatted(to, checkSymbol)

                    Piece.king -> "%s".repeat(4).formatted(letter, capture, to, checkSymbol)
                    Piece.knight, Piece.bishop, Piece.rook, Piece.queen -> {
                        val disambiguation: List<Square.With<Piece>> = piecesMatching { candidate ->
                            candidate.type === type && candidate.side === side && candidate.pos() != from &&
                                    squaresAttackedByPiece(candidate).any { square -> square == to }
                        }
                            .toList()

                        var dis: String? = ""
                        if (!disambiguation.isEmpty()) {
                            data class Unique(val file: Boolean, val rank: Boolean)

                            /**
                             * public inline fun <S, T : S> Sequence<T>.reduce(operation: (acc: S, T) -> S): S {
                             *
                             */
                            val unique = disambiguation.fold(
                                Unique(file = true, rank = true)
                            ) { result, piece ->
                                Unique(
                                    result.file && piece.file() != from.file(),
                                    result.rank && piece.rank() != from.rank()
                                )
                            }

                            if (unique.file && unique.rank) {
                                dis = from.toString().substring(0, 1) // specify the file
                            } else if (unique.file) {
                                dis = from.toString().substring(0, 1) // specify the file
                            } else if (unique.rank) {
                                dis = from.toString().substring(1, 2) // specify the rank
                            } else {
                                dis = from.toString() // specify both file and rank
                            }
                        }
                        "%s".repeat(5).formatted(letter, dis, capture, to, checkSymbol)
                    }
                }
            }

            is Castling -> {
                val castleside = if (internalMove.king.from.file() < internalMove.rook.from.file()) "O-O" else "O-O-O"
                "%s".repeat(2).formatted(castleside, checkSymbol)
            }

            is Promotion -> {
                val to = internalMove.pawn.to
                val from = internalMove.pawn.from
                val toPiece = internalMove.piece
                val promotion = "=" + String.valueOf(toPiece.toChar(Side.white))
                if (this.squareMap[to] is Square.With) {
                    // bxa8=R
                    "%s".repeat(5).formatted(from.file(), "x", to, promotion, checkSymbol)
                } else {
                    // a8=Q
                    "%s".repeat(3).formatted(to, promotion, checkSymbol)
                }
            }
            else -> {
                throw IllegalStateException("Unknown move type: $internalMove")
            }
        }
    }

    fun withFEN(_fen: FEN): NaiveChess {
        val _squareMap = DefaultBoard.fenPositionsToSquares(_fen.positions())
        return NaiveChess(variant(), _fen, _squareMap, this.rookFiles)
    }

    private fun _play(internalMove: InternalMove): NaiveChess {
        val mutableMap = HashMap(this.squareMap)

        var nextFen: FEN = FEN.parse(this.fen.toString())

        var resetHalfMoveClock = false
        var ep = "-"
        var maybeCapture: Square<Piece>? = null
        when (internalMove) {
            is FromTo-> {
                val to = internalMove.to
                val from = internalMove.from.pos
                val type = internalMove.from.type
                val side = internalMove.from.side

                mutableMap.put(from, Square.empty(from))
                maybeCapture = mutableMap.put(to, withPiece(to, type, side))

                when (type) {
                    Piece.pawn -> {
                        resetHalfMoveClock = true

                        if (from.file() != to.file()) {
                            // capture

                            if (maybeCapture is Square.Empty) {
                                // Aha! Must have been en passant!
                                // Put empty square where pawn was
                                val epSquare: Pos = Square.pos(to.file(), from.rank())
                                mutableMap.put(epSquare, Square.empty(epSquare))
                            }
                        }

                        val distance: Int = from.rank() - to.rank()
                        if (abs(distance) == 2) {
                            val square1 = mutableMap[
                                to.delta(
                                    0,
                                    -1
                                )
                            ]

                            val square2 = mutableMap[
                                to.delta(
                                    0,
                                    +1
                                )
                            ]
                            if (square1 is Square.With && square1.type === Piece.pawn && square1.side === side.other()) {
                                ep = to.delta(distance / 2, 0).toString()
                            } else if (square2 is Square.With && square2.type === Piece.pawn && square2.side === side.other()) {
                                ep = to.delta(distance / 2, 0).toString()
                            }
                        }
                    }

                    Piece.king -> {
                        nextFen = nextFen.withCastling(
                            nextFen.castling().toCharArray()
                                .filter(
                                    { i ->
                                        if (this.fen.side() === Side.black)
                                            Character.isUpperCase(i)
                                        else
                                            Character.isLowerCase(i)
                                    },
                                ).joinToString(transform = { i -> String.valueOf(i) })
                        )
                    }

                    Piece.rook -> {
                        if (castlingRookFiles(this.fen.side()).contains(from.file()) &&
                            from.rank() == (if (this.fen.side() == Side.white) 1 else 8)
                        ) {
                            val king: Pos? = piecesMatching { candidate ->
                                candidate.side === fen.side() &&
                                        candidate.type === Piece.king
                            }
                                .firstOrNull()
                                ?.pos()

                            if (king != null) {
                                val file = if (this.fen.side() === Side.white)
                                    String.valueOf(from.file()).uppercase()
                                else
                                    String.valueOf(from.file())

                                nextFen = nextFen.withCastling(nextFen.castling().replace(file, ""))

                                if (king.file() < from.file()) {
                                    // kingside
                                    val k = if (this.fen.side() === Side.white) "K" else "k"
                                    nextFen = nextFen.withCastling(nextFen.castling().replace(k, ""))
                                } else {
                                    // queenside
                                    val q = if (this.fen.side() === Side.white) "Q" else "q"
                                    nextFen = nextFen.withCastling(nextFen.castling().replace(q, ""))
                                }
                            }
                        }
                    }

                    Piece.bishop, Piece.knight, Piece.queen -> {}
                }
            }

            is Promotion -> {
                val fromTo = internalMove.pawn
                val promotion = internalMove.piece
                mutableMap.put(fromTo.from.pos(), Square.empty(fromTo.from.pos()))
                maybeCapture = mutableMap.put(fromTo.to,
                    withPiece(fromTo.to, promotion, fromTo.from.side)
                )
                resetHalfMoveClock = true
            }

            is Castling -> {
                val king = internalMove.king
                val rook = internalMove.rook

                mutableMap.put(king.from.pos(), Square.empty(king.from.pos()))
                mutableMap.put(rook.from.pos(), Square.empty(rook.from.pos()))

                mutableMap.put(king.to, withPiece(king.to, king.from.type, king.from.side))
                mutableMap.put(rook.to, withPiece(rook.to, rook.from.type, rook.from.side))

                nextFen = nextFen.withCastling(
                    nextFen.castling().toCharArray()
                        .filter(
                            { i ->
                                if (this.fen.side() === Side.black)
                                    Character.isUpperCase(i)
                                else
                                    Character.isLowerCase(i)
                            },
                        ).joinToString(transform = { i -> String.valueOf(i) })
                )
            }
        }

        // capture (FromTo or Promotion(FromTo))
        if (maybeCapture is Square.With) {
            resetHalfMoveClock = true
            val capturedPiece = maybeCapture.type
            val capturedSide = maybeCapture.side
            val capturedPos = maybeCapture.pos

            if (capturedPiece === Piece.rook && castlingRookFiles(capturedSide).contains(capturedPos.file()) && capturedPos.rank() == (if (capturedSide === Side.white) 1 else 8)) {
                val king: Pos? = piecesMatching { candidate ->
                    candidate.side === capturedSide &&
                            candidate.type === Piece.king
                }
                    .firstOrNull()
                    ?.pos()

                if (king != null) {
                    val file = if (capturedSide === Side.white)
                        String.valueOf(capturedPos.file()).uppercase()
                    else
                        String.valueOf(capturedPos.file())

                    nextFen = nextFen.withCastling(nextFen.castling().replace(file, ""))

                    if (king.file() < capturedPos.file()) {
                        // kingside
                        val k = if (capturedSide === Side.white) "K" else "k"
                        nextFen = nextFen.withCastling(nextFen.castling().replace(k, ""))
                    } else {
                        // queenside
                        val q = if (capturedSide === Side.white) "Q" else "q"
                        nextFen = nextFen.withCastling(nextFen.castling().replace(q, ""))
                    }
                }
            }
        }

        nextFen = nextFen
            .withPositions(DefaultBoard.squaresToFenPositions(mutableMap))
            .withSide(this.fen.side().other())
            .withEP(ep)
            .withHalfMove(if (resetHalfMoveClock) 0 else this.fen.halfMove() + 1)
            .withMove(if (this.fen.side() === Side.black) this.fen.move() + 1 else this.fen.move())

        val nextBoard = withFEN(nextFen)

        return nextBoard
    }

    private fun toInternalMove(moveStr: String): InternalMove {
        val uci = anyToUCI(moveStr)

        val fromPos: Pos? = Square.pos(uci.substring(0, 2))
        val toPos = Square.pos(uci.substring(2, 4))

        val square = this.squareMap.get(fromPos)

        if (!(square is Square.With<Piece> && square.side === this.fen.side())) {
            throw IllegalArgumentException("No piece of side ${this.fen.side()} at position $fromPos!")
        }

        val fromToMove = FromTo(square, toPos)
        var move: InternalMove = fromToMove

        if (uci.length == 5) {
            move = Promotion(
                fromToMove, when (uci[4]) {
                    'q' -> Piece.queen
                    'n' -> Piece.knight
                    'b' -> Piece.bishop
                    'r' -> Piece.rook
                    else -> Piece.queen
                }
            )
        }

        val validMoves = validMovesByPiece(square).toSet()
        if (!validMoves.contains(move)) {
            return validMoves.asSequence()
                .filter { m ->
                    m is Castling &&
                            (m.king == fromToMove || (fromToMove.from == m.king.from &&
                                fromToMove.to == m.rook.from.pos())
                                    )
                }
                .firstOrNull() ?: throw NoSuchElementException("No valid castling move found")
        }
        return move
    }

    fun anyToUCI(any: String): String {
        var chars = any.toCharArray()

        // check if already uci
        if (chars.size >= 4 && chars[0] >= 'a' && chars[0] <= 'h' && chars[1] >= '1' && chars[1] <= '8' && chars[2] >= 'a' && chars[2] <= 'h' && chars[3] >= '1' && chars[3] <= '8') {
            return any
        }

        // Not UCI format, maybe SAN, i.e
        // "Nf3", "e4", "exd5", "O-O", "0-0"
        // "Qxf7+", "Qf7#"
        // "Rad1"
        var move = any.replace("x", "").replace("#", "").replace("+", "")
        chars = move.toCharArray()

        when (move) {
            "O-O", "0-0" -> {
                val kingPos = piecesMatching { candidate ->
                    candidate.side === fen.side() &&
                            candidate.type === Piece.king
                }
                    .firstOrNull()
                    ?.pos
                if (kingPos == null) return ""

                val rookPos: Pos? = castlingRookFiles(this.fen.side()).asSequence()
                    .filter { file -> kingPos.file() < file }
                    .map { file -> Square.pos(file, kingPos.rank()) }
                    .firstOrNull()
                if (rookPos == null) return ""

                return "%s%s".formatted(kingPos, rookPos) // king-onto-rook
            }

            "O-O-O", "0-0-0" -> {
                val kingPos: Pos? = piecesMatching { candidate ->
                    candidate.side === fen.side() &&
                            candidate.type === Piece.king
                }
                    .firstOrNull()
                    ?.pos
                if (kingPos == null) return ""

                val rookPos: Pos? = castlingRookFiles(this.fen.side()).asSequence()
                    .filter { file -> kingPos.file() > file }
                    .map { file -> Square.pos(file, kingPos.rank()) }
                    .firstOrNull()
                if (rookPos == null) return ""

                return "%s%s".formatted(kingPos, rookPos) // king-onto-rook
            }
        }

        return when (chars[0]) {
            'N', 'B', 'R', 'Q', 'K' -> {
                // Piece move, like Qe1, or disambiguated queen move like Qhe1, Q4e1, Qh4e1

                // Q

                val typeChar = chars[0]

                // e1
                val to: Pos? = Square.pos(move.substring(move.length - 2))

                // Check disambiguation: |file| or |rank| or |fileandrank|
                // Q|h|e1 or Q|4|e1 or Q|h4|e1
                val disambiguation = move.substring(1, move.length - 2)

                // Q|h4|e1
                if (disambiguation.length == 2) {
                    "%s%s".formatted(disambiguation, to)
                }

                // Q|h|e1 or Q|4|e1
                if (disambiguation.length == 1) {
                    val fileOrRank: Char = disambiguation[0]
                    if (fileOrRank >= '1' && fileOrRank <= '8') {
                        // rank
                        val from: Pos? = piecesMatching { candidate ->
                            candidate.side == this.fen.side() && candidate.type
                                .toChar(Side.white) == typeChar && candidate.pos()
                                .rank() == fileOrRank.digitToInt()
                        }
                            .filter { candidate ->
                                validMovesByPiece(candidate)
                                    .map { validMove -> validMove.toUCI(variant()) }
                                    .any { str -> str == "%s%s".formatted(candidate.pos(), to) }
                            }
                            .map { it.pos }
                            .firstOrNull()
                        if (from == null) ""
                        "%s%s".formatted(from, to)
                    } else {
                        // file
                        val from: Pos? = piecesMatching { candidate ->
                            candidate.side === this.fen.side() && candidate.type
                                .toChar(Side.white) == typeChar && candidate.pos().file() == fileOrRank
                        }
                            .filter { candidate ->
                                validMovesByPiece(candidate)
                                    .map { validMove -> validMove.toUCI(variant()) }
                                    .any { str -> str == "%s%s".formatted(candidate.pos(), to) }
                            }
                            .map { it.pos }
                            .firstOrNull()
                        if (from == null) ""
                        "%s%s".formatted(from, to)
                    }
                }

                // No disambiguation, so only one Q can make it to e1, find it
                val from: Pos? = piecesMatching { candidate ->
                    candidate.side === this.fen.side() &&
                            candidate.type.toChar(Side.white) == typeChar
                }
                    .filter { candidate ->
                        validMovesByPiece(candidate)
                            .map { validMove -> validMove.toUCI(variant()) }
                            .any { str -> str == "%s%s".formatted(candidate.pos(), to) }
                    }
                    .map { it.pos }
                    .firstOrNull()
                if (from == null) ""
                "%s%s".formatted(from, to)
            }

            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' -> {
                // pawn move
                val file = chars[0]

                // a1=Q -> a2a1q
                var promotion: String? = ""
                if (move.contains("=")) {
                    promotion = move.substring(move.indexOf("=") + 1).lowercase()
                    move = move.substring(0, move.indexOf("="))
                }
                val to: Pos? = Square.pos(move.substring(move.length - 2))

                // d3    d2d3
                // d4    d2d4 / d3d4
                // dc6
                val from: Pos? = piecesMatching { candidate ->
                    candidate.type === Piece.pawn && candidate.side === this.fen.side() && candidate.pos()
                        .file() == file
                }
                    .filter { candidate ->
                        validMovesByPiece(candidate)
                            .map { validMove -> validMove.toUCI(variant()) }
                            .map { str -> str.substring(0, 4) } // truncate any promotion piece
                            .any { str -> str == "%s%s".formatted(candidate.pos(), to) }
                    }
                    .map { it.pos }
                    .firstOrNull()
                if (from == null) ""
                "%s%s".formatted(from, to.toString() + promotion)
            }

            else -> ""
        }
    }

    fun piecesMatching(filter: (Square.With<Piece>) -> Boolean): Sequence<Square.With<Piece>> {
        return this.squareMap.values.asSequence()
            .mapNotNull { square ->
                if (square is Square.With<Piece> && filter(square))
                    square
                else
                    null
            }
    }

    internal interface InternalMove {
        fun toUCI(variant: String): String {
            return when (this) {
                is FromTo -> "%s%s".formatted(from.pos(), to)
                is Promotion -> "%s%s%s".formatted(pawn.from.pos(), pawn.to, piece.toChar(Side.black))
                is Castling -> when (variant) {
                    "standard" -> if (rook.from.file() == 'a')
                        "%s%s".formatted(king.from.pos(), "c" + king.from.pos().rank())
                    else
                        "%s%s".formatted(king.from.pos(), "g" + king.from.pos().rank())
                    else -> "%s%s".formatted(king.from.pos(), rook.from.pos())
                }
                else -> ""
            }
        }
    }

    internal class FromTo(val from: Square.With<Piece>, val to: Pos) : InternalMove {
    }

    @JvmRecord
    internal data class Castling(val king: FromTo, val rook: FromTo) : InternalMove
    internal class Promotion(val pawn: FromTo, val piece: Piece) : InternalMove {
    }

    private fun validMovesByPiece(piece: Square.With<Piece>): Sequence<InternalMove> {
        val attackedSquares = squaresAttackedByPiece(piece)

        val movesByPiece = when (piece.type) {
            Piece.knight, Piece.bishop, Piece.rook, Piece.queen -> attackedSquares
                .mapNotNull { pos ->
                    val square = this.squareMap.get(pos)
                    if (square is Square.Empty ||
                        (square is Square.With<Piece> && square.side === this.fen.side().other())
                    ) {
                        FromTo(piece, pos)
                    } else {
                        null
                    }
                }

            Piece.pawn -> {
                var fromTo: Sequence<FromTo> = attackedSquares
                    .mapNotNull { pos ->
                        val square = this.squareMap.get(pos)
                        if (square is Square.With && square.side == this.fen.side().other()) {
                            FromTo(piece, pos)
                        } else {
                            null
                        }
                    }

                val dir = if (piece.side === Side.black) -1 else 1
                val oneForward = FromTo(piece, piece.pos().delta(dir, 0))

                if (this.squareMap.get(oneForward.to) is Square.Empty) {
                    fromTo = fromTo + sequenceOf(oneForward)

                    if (piece.side === Side.black && piece.rank() == 7 ||
                        piece.side === Side.white && piece.rank() == 2
                    ) {
                        val twoForward =
                            FromTo(piece, piece.pos().delta(dir * 2, 0))

                        if (this.squareMap.get(twoForward.to) is Square.Empty) {
                            fromTo = fromTo + sequenceOf(twoForward)
                        }
                    }
                }

                fromTo.flatMap { move ->
                    if (move.to.rank() == 1 || move.to.rank() == 8) {
                        listOf(Piece.knight, Piece.bishop, Piece.rook, Piece.queen)
                            .asSequence()
                            .map { type -> Promotion(move, type) }
                    } else {
                        sequenceOf(move)
                    }
                }

            }

            Piece.king -> {
                val moves: Sequence<InternalMove> = attackedSquares.mapNotNull { pos ->
                    val square = this.squareMap.get(pos)
                    if (square is Square.With && square.side === this.fen.side().other()
                            || square is Square.Empty) {
                        FromTo(piece, pos)
                    } else {
                        null
                    }
                }

                var castlings: Sequence<InternalMove> = emptySequence()

                val rookFiles= castlingRookFiles(this.fen.side())

                if (!rookFiles.isEmpty()) {
                    val rank = if (this.fen.side() === Side.black) 8 else 1
                    for (file in rookFiles) {
                        val rook = this.squareMap.get(Square.pos(file, rank))
                        if (!(rook is Square.With<Piece> && rook.type === Piece.rook && rook.side === this.fen.side())
                        ) {
                            println(
                                """
                                    Huh. Couldn't find rook for castling!
                                    file: %s
                                    Variant: %s
                                    FEN: %s

                                    """.trimIndent().formatted(file, variant(), this.fen)
                            )
                            Exception().printStackTrace()
                            continue
                        }

                        val castling: Castling = if (rook.pos().file() < piece.file())
                            Castling(
                                FromTo(
                                    piece,
                                    Square.pos('c', rank)
                                ), FromTo(rook, Square.pos('d', rank))
                            )
                        else
                            Castling(
                                FromTo(
                                    piece,
                                    Square.pos('g', rank)
                                ), FromTo(rook, Square.pos('f', rank))
                            )

                        // check that rook doesn't move through pieces (other than self and own king)
                        val rookSquares: Set<Pos?> = travelSquares(castling.rook)
                        if (blocked(rookSquares, setOf(piece, rook))) continue

                        // check that king doesn't move through pieces (other than the castling rook)
                        val kingSquares: Set<Pos?> = travelSquares(castling.king)
                        val kingBlocked = blocked(kingSquares, setOf(piece, rook))
                        if (kingBlocked) continue

                        // check that king doesn't move through check
                        val kingMovesThroughCheck: Boolean =
                            piecesMatching { candidate -> candidate.side === this.fen.side().other() }
                                .any { candidate -> squaresAttackedByPiece(candidate).any(kingSquares::contains) }
                        if (kingMovesThroughCheck) continue

                        castlings = castlings + sequenceOf(castling)
                    }
                }

                moves + castlings
            }
        }

        return movesByPiece
            .filter { move ->
                when (move) {
                    is FromTo -> !isSelfCheck(move)
                    is Promotion -> !isSelfCheck(move.pawn)
                    is Castling -> true
                    else -> false
                }
            }
    }

    private fun travelSquares(piece: FromTo): Set<Pos?> {
        return (min(piece.from.file().code, piece.to.file().code)..
            max(piece.from.file().code, piece.to.file().code))
            .map { i -> Square.pos(i.toChar(), piece.from.rank()) }
            .toSet()
    }

    fun castlingRookFiles(side: Side): List<Char> {
        return fen.castling().toCharArray()
            .filter({ i -> if (side === Side.black) Character.isLowerCase(i) else Character.isUpperCase(i) })
            .map {
                it.lowercaseChar()
            }
            .map { i ->
                when (i) {
                    'k' -> this.rookFiles.k
                    'q' -> this.rookFiles.q
                    else -> i
                }
            }.toList()
    }

    fun blocked(squares: Set<Pos?>, ignoredPieces: Set<Square.With<Piece>>): Boolean {
        return piecesMatching { candidate -> !ignoredPieces.contains(candidate) && squares.contains(candidate.pos()) }
            .firstOrNull() != null
    }

    private fun isSelfCheck(fromTo: FromTo): Boolean {
        // Check if move would result in current side being in check

        try {
            val kingPos = if (fromTo.from.type === Piece.king)
                fromTo.to
            else
                piecesMatching { candidate ->
                    candidate.side === fen.side() &&
                        candidate.type === Piece.king
                }
                    .firstOrNull()
                    ?.pos
                    ?: throw NoSuchElementException("No king found")


            val mutableMap = HashMap(this.squareMap)
            mutableMap.put(fromTo.from.pos(), Square.empty(fromTo.from.pos()))
            mutableMap.put(fromTo.to, withPiece(fromTo.to, fromTo.from.type, fromTo.from.side))

            val mutatedPositions = DefaultBoard.squaresToFenPositions(mutableMap)

            val mutatedBoard = withFEN(
                this.fen
                    .withPositions(mutatedPositions)
                    .withSide(this.fen.side().other())
            )

            return mutatedBoard.piecesMatching { candidate ->
                    candidate.side === this.fen.side().other() &&
                        mutatedBoard.squaresAttackedByPiece(candidate)
                            .any { square -> square == kingPos }
                }
                .firstOrNull() != null
        } catch (_: Exception) {
            return true
        }
    }

    @JvmRecord
    data class RookFiles(val k: Char, val q: Char)

    @JvmRecord
    internal data class Dir(val dx: Int, val dy: Int)

    fun squaresAttackedByPiece(piece: Square.With<Piece>): Sequence<Pos> {
        val pos: Pos = piece.pos()
        val unboundedCoordinates: Sequence<Pos> = when (piece.type) {
            Piece.pawn -> sequenceOf(
                pos.delta(if (piece.side === Side.black) -1 else 1, +1),
                pos.delta(if (piece.side === Side.black) -1 else 1, -1)
            )

            Piece.bishop -> reachableSquaresInDirectionsFromSquare(
                pos,
                listOf(
                    Dir(-1, 1),
                    Dir(1, 1),
                    Dir(-1, -1),
                    Dir(1, -1)
                )
            )

            Piece.rook -> reachableSquaresInDirectionsFromSquare(
                pos,
                listOf(
                    Dir(0, 1),
                    Dir(0, -1),
                    Dir(1, 0),
                    Dir(-1, 0)
                )
            )

            Piece.queen -> reachableSquaresInDirectionsFromSquare(
                pos,
                listOf(
                    Dir(0, 1),
                    Dir(0, -1),
                    Dir(1, 0),
                    Dir(-1, 0),
                    Dir(-1, 1),
                    Dir(1, 1),
                    Dir(-1, -1),
                    Dir(1, -1)
                )
            )

            Piece.king -> sequenceOf(
                pos.delta(-1, -1), pos.delta(-1, 0), pos.delta(-1, 1),
                pos.delta(0, -1), pos.delta(0, 1),
                pos.delta(1, -1), pos.delta(1, 0), pos.delta(1, 1)
            )

            Piece.knight -> sequenceOf(
                pos.delta(-2, -1), pos.delta(-2, 1),
                pos.delta(-1, -2), pos.delta(-1, 2),
                pos.delta(1, -2), pos.delta(1, 2),
                pos.delta(2, -1), pos.delta(2, 1)
            )
        }
        return unboundedCoordinates
            .filter { candidate -> candidate.rank() >= 1 && candidate.rank() <= 8 && candidate.file() >= 'a' && candidate.file() <= 'h' }
    }


    private fun reachableSquaresInDirectionsFromSquare(pos: Pos, directions: List<Dir?>): Sequence<Pos> {
        return sequence {
            for (dir in directions) {
                var r = pos.rank() + dir!!.dy
                var c = pos.file() + dir.dx
                while (r >= 1 && r <= 8 && c >= 'a' && c <= 'h') {
                    val current: Pos = Square.pos(c, r)
                    yield(current)
                    val square = this@NaiveChess.squareMap.get(current)
                    if (square is Square.With) break
                    r += dir.dy
                    c = (c.code + dir.dx).toChar()
                }
            }
        }
    }

    companion object {
        fun of(variant: String, fenString: String): NaiveChess {
            val fen: FEN = FEN.parse(fenString)
            val squareMap = DefaultBoard.fenPositionsToSquares(fen.positions())
            val rookFiles: RookFiles =
                initRookFiles(variant, fen.castling(), squareMap)
            return NaiveChess(variant, fen, squareMap, rookFiles)
        }

        fun initRookFiles(variant: String, castling: String, squareMap: Map<Pos, Square<Piece>>): RookFiles {
            return when (variant) {
                "chess960" -> {
                    var k = ' '
                    var q = ' '

                    val whiteKingFile: Char =
                        findPieceFile(0, 7, Piece.king, 1, squareMap)
                    val blackKingFile: Char =
                        findPieceFile(0, 7, Piece.king, 8, squareMap)

                    for (c in castling.toCharArray()) {
                        val lowercase: Char = c.lowercaseChar()
                        val rank = if (Character.isLowerCase(c)) 8 else 1
                        val kingFile = if (Character.isLowerCase(c)) blackKingFile else whiteKingFile

                        if (lowercase >= 'a' && lowercase <= 'h') {
                            val square = squareMap.get(Square.pos(lowercase, rank))
                            if (square is Square.With<Piece> && square.type === Piece.rook) {
                                if (kingFile < lowercase) {
                                    k = lowercase
                                } else {
                                    q = lowercase
                                }
                            }
                        } else if (lowercase == 'k') {
                            k = findPieceFile(
                                kingFile.code - 'a'.code,
                                7,
                                Piece.rook,
                                rank,
                                squareMap
                            )
                        } else if (lowercase == 'q') {
                            q = findPieceFile(
                                0,
                                kingFile.code - 'a'.code,
                                Piece.rook,
                                rank,
                                squareMap
                            )
                        }
                    }
                    RookFiles(k, q)
                }

                else -> RookFiles('h', 'a')
            }
        }

        fun findPieceFile(
            rangeStart: Int,
            rangeEnd: Int,
            piece: Piece?,
            rank: Int,
            squareMap: Map<Pos, Square<Piece>>
        ): Char {
            return (rangeStart..rangeEnd).asSequence()
                .filter { i ->
                    val square = squareMap.get(Square.pos('a' + i, rank))
                    square is Square.With<Piece> && square.type === piece
                }
                .map { i -> 'a' + i }
                .firstOrNull() ?: ' '
        }
    }
}
