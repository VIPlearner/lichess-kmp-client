package com.viplearner.chess

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TestBoard {
    @Test
    fun capture() {
        val moves = "d4 e5 dxe5"

        val board: DefaultBoard = DefaultBoard.ofStandard().play(moves)

        assertTrue(board.pieces().captured(Side.white) == listOf<Piece>())
        assertTrue(board.pieces().captured(Side.black) == listOf(Piece.pawn))
        assertTrue(
            board.pieces().all(Side.black).size <
                board.pieces()
                    .all(Side.white).size,
        )

        var boardIncremental: DefaultBoard = DefaultBoard.ofStandard()
        for (move in moves.split(" ")) boardIncremental = boardIncremental.play(move)
        assertTrue(boardIncremental.pieces().captured(Side.white) == listOf<Piece>())
        assertTrue(boardIncremental.pieces().captured(Side.black) == listOf(Piece.pawn))
        assertTrue(
            boardIncremental.pieces().all(Side.black).size <
                boardIncremental.pieces()
                    .all(Side.white).size,
        )
    }

    @Test
    fun naive() {
        val nc: NaiveChess = NaiveChess.of("standard", FEN.standardStr)
        val moves: Collection<String?> = nc.validMoves()
        // moves.stream().sorted().forEach(IO::println);
        assertTrue(20 == moves.size)
    }

    @Test
    fun castling() {
        val board: Board = Board.fromFEN("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1")
        val moves: Collection<String?> = board.validMoves()
        // moves.stream().sorted().forEach(IO::println);
        assertTrue(moves.contains("e1c1"))
        assertTrue(moves.contains("e1g1"))
    }

    @Test
    fun castlingChess960() {
        val boardKQ: Board = Board.ofChess960("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1")
        val boardHA: Board = Board.ofChess960("r3k2r/8/8/8/8/8/8/R3K2R w HAha - 0 1")
        val movesKQ: Collection<String?> = boardKQ.validMoves()
        val movesHA: Collection<String?> = boardHA.validMoves()
        // movesKQ.stream().sorted().forEach(IO::println);
        // movesHA.stream().sorted().forEach(IO::println);
        assertTrue(movesKQ.contains("e1a1"))
        assertTrue(movesKQ.contains("e1h1"))
        assertTrue(movesHA.contains("e1a1"))
        assertTrue(movesHA.contains("e1h1"))
    }

    @Test
    fun castlingChess960Pos10() {
        var boardKQ: Board =
            Board.ofChess960("qnnrbbkr/pppppppp/8/8/8/8/PPPPPPPP/QNNRBBKR w KQkq - 0 1")
        var boardDH: Board = Board.ofChess960(10)

        assertEquals("qnnrbbkr/pppppppp/8/8/8/8/PPPPPPPP/QNNRBBKR w DHdh - 0 1", boardDH.toFEN())

        val moves = "e3 e6 f3 f6 Bg3 Bg6 Bd3 Bd6 Ne2 Ne7"
        boardKQ = boardKQ.play(moves)
        boardDH = boardDH.play(moves)

        val movesKQ: Collection<String?> = boardKQ.validMoves()
        val movesDH: Collection<String?> = boardDH.validMoves()

        assertTrue(movesKQ.contains("g1d1"))
        assertTrue(movesKQ.contains("g1h1"))
        assertTrue(movesDH.contains("g1d1"))
        assertTrue(movesDH.contains("g1h1"))
    }

    @Test
    fun toUCI() {
        val board: Board = Board.ofStandard()
        assertEquals("b1c3", board.toUCI("Nc3"))
    }

    @Test
    fun standardFen() {
        val board: Board = Board.ofStandard()
        assertEquals(standardFen, board.toFEN())
    }

    @Test
    fun customStandardFen() {
        val board: Board = Board.fromFEN(standardFen)
        assertEquals(standardFen, board.toFEN())
    }

    @Test
    fun threefoldRepetition() {
        var board: Board = Board.ofStandard()
        board = board.play("Na3 Na6 Nb1 Nb8 Na3 Na6 Nb1")

        assertFalse(board.validMoves().isEmpty())

        // third time position shows up
        board = board.play("Nb8")

        assertTrue(board.validMoves().isEmpty())
    }

    @Test
    fun disambiguationTwoQueensOnSameFile() {
        val board: Board =
            Board.fromFEN("rn1k4/pb3r2/q2b4/1p1p4/2P1p3/1K1P1p1p/P3P2P/qNBQ1B1R b -- 0 50")
        val san: String? = board.toSAN("a6a2")

        assertEquals("Q6xa2#", san)
    }

    @Test
    fun disambiguations() {
        val board: Board = Board.fromFEN("k7/pp6/8/5Q2/4Q2Q/8/5Q1Q/K7 w - - 0 1")
        assertEquals("Qef4", board.toSAN("e4f4")) // specify file
        assertEquals("Q5f4", board.toSAN("f5f4")) // specify rank
        assertEquals("Qh2g3", board.toSAN("h2g3")) // specify file and rank
    }

    @Test
    fun castlingUciKingToRookOrFinalSquare() {
        val boardBeforeCastling: Board =
            Board.fromFEN("r3kb1r/pbpn1ppp/1p1p4/1P6/P1P2P2/4K2P/8/1q5n b kq - 3 18")

        val kingToRook = "e8a8"
        val castlingKingToRook = boardBeforeCastling.play(kingToRook)

        val kingToTarget = "e8c8"
        val castlingKingToTarget = boardBeforeCastling.play(kingToTarget)

        assertEquals(
            "2kr1b1r/pbpn1ppp/1p1p4/1P6/P1P2P2/4K2P/8/1q5n w - - 4 19",
            castlingKingToTarget.toFEN(),
        )
        assertEquals(castlingKingToRook, castlingKingToTarget)
    }

    @Test
    fun cantCastleIntoCheck() {
        val boardBeforeIllegalCastling: Board =
            Board.fromFEN("rn1qk2r/p1p1p2p/1p1p2Rn/5p1P/2b1P3/3P4/PBP2PP1/RN1QKB2 b Qkq - 0 13")
        val afterIllegalCastlingE8G8: Board? = boardBeforeIllegalCastling.play("e8g8")
        val afterIllegalCastlingE8H8: Board? = boardBeforeIllegalCastling.play("e8h8")

        assertTrue(boardBeforeIllegalCastling.equals(afterIllegalCastlingE8G8))
        assertTrue(boardBeforeIllegalCastling.equals(afterIllegalCastlingE8H8))
    }

    @Test
    fun revokedCastlingRightsWhite() {
        val whiteToMove: Board = Board.fromFEN("r3k2r/4p3/8/8/8/8/4P3/R3K2R w KQkq - 0 1")

        assertTrue(
            whiteToMove.validMoves().contains("e1c1"),
        )
        assertTrue(
            whiteToMove.validMoves().contains("e1g1"),
        )

        val blackSimulatedNopMove = "e7e6"

        val whiteToMoveAfterMovingA1: Board = whiteToMove.play("a1a2").play(blackSimulatedNopMove)
        assertFalse(
            whiteToMoveAfterMovingA1.validMoves()
                .contains("e1c1"),
        )
        assertTrue(
            whiteToMoveAfterMovingA1.validMoves()
                .contains("e1g1"),
        )

        val whiteToMoveAfterMovingH1: Board = whiteToMove.play("h1h2").play(blackSimulatedNopMove)
        assertTrue(
            whiteToMoveAfterMovingH1.validMoves()
                .contains("e1c1"),
        )
        assertFalse(
            whiteToMoveAfterMovingH1.validMoves()
                .contains("e1g1"),
        )

        val whiteToMoveAfterMovingKing: Board = whiteToMove.play("e1d2").play(blackSimulatedNopMove)
        assertFalse(
            whiteToMoveAfterMovingKing.validMoves()
                .contains("e1c1"),
        )
        assertFalse(
            whiteToMoveAfterMovingKing.validMoves()
                .contains("e1g1"),
        )
    }

    @Test
    fun revokedCastlingRightsBlack() {
        val blackToMove: Board = Board.fromFEN("r3k2r/4p3/8/8/8/8/4P3/R3K2R b KQkq - 0 1")
        assertTrue(
            blackToMove.validMoves().contains("e8c8"),
        )
        assertTrue(
            blackToMove.validMoves().contains("e8g8"),
        )

        val whiteSimulatedNopMove = "e2e3"

        val blackToMoveAfterMovingA8: Board = blackToMove.play("a8a7").play(whiteSimulatedNopMove)
        assertFalse(
            blackToMoveAfterMovingA8.validMoves()
                .contains("e8c8"),
        )
        assertTrue(
            blackToMoveAfterMovingA8.validMoves()
                .contains("e8g8"),
        )

        val blackToMoveAfterMovingH8: Board = blackToMove.play("h8h7").play(whiteSimulatedNopMove)
        assertTrue(
            blackToMoveAfterMovingH8.validMoves()
                .contains("e8c8"),
        )
        assertFalse(
            blackToMoveAfterMovingH8.validMoves()
                .contains("e8g8"),
        )

        val blackToMoveAfterMovingKing: Board = blackToMove.play("e8d7").play(whiteSimulatedNopMove)
        assertFalse(
            blackToMoveAfterMovingKing.validMoves()
                .contains("e8c8"),
        )
        assertFalse(
            blackToMoveAfterMovingKing.validMoves()
                .contains("e8g8"),
        )
    }

    @Test
    fun whiteCastling() {
        val whiteToMove: Board = Board.fromFEN("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1")

        val expectedFenAfterQueenSide = "r3k2r/8/8/8/8/8/8/2KR3R b kq - 1 1"
        val expectedFenAfterKingSide = "r3k2r/8/8/8/8/8/8/R4RK1 b kq - 1 1"

        assertTrue(
            whiteToMove.validMoves().contains("e1c1"),
        )
        assertTrue(
            whiteToMove.validMoves().contains("e1g1"),
        )

        val queenSideByOOO: Board = whiteToMove.play("O-O-O")
        val queenSideBy000: Board = whiteToMove.play("0-0-0")
        val queenSideByE1C1: Board = whiteToMove.play("e1c1")

        // Board queenSideByE1A1 = whiteToMove.play("e1a1");
        assertEquals(expectedFenAfterQueenSide, queenSideByOOO.toFEN())
        assertEquals(expectedFenAfterQueenSide, queenSideBy000.toFEN())
        assertEquals(expectedFenAfterQueenSide, queenSideByE1C1.toFEN())

        val kingSideByOO: Board = whiteToMove.play("O-O")
        val kingSideBy00: Board = whiteToMove.play("0-0")
        val kingSideByE1G1: Board = whiteToMove.play("e1g1")

        // Board kingSideByE1H1 = whiteToMove.play("e1h1");
        assertEquals(expectedFenAfterKingSide, kingSideByOO.toFEN())
        assertEquals(expectedFenAfterKingSide, kingSideBy00.toFEN())
        assertEquals(expectedFenAfterKingSide, kingSideByE1G1.toFEN())
    }

    @Test
    fun blackCastling() {
        val blackToMove: Board = Board.fromFEN("r3k2r/8/8/8/8/8/8/R3K2R b KQkq - 0 1")

        val expectedFenAfterQueenSide = "2kr3r/8/8/8/8/8/8/R3K2R w KQ - 1 2"
        val expectedFenAfterKingSide = "r4rk1/8/8/8/8/8/8/R3K2R w KQ - 1 2"

        assertTrue(
            blackToMove.validMoves().contains("e8c8"),
        )
        assertTrue(
            blackToMove.validMoves().contains("e8g8"),
        )

        val queenSideByOOO: Board = blackToMove.play("O-O-O")
        val queenSideBy000: Board = blackToMove.play("0-0-0")
        val queenSideByE8C8: Board = blackToMove.play("e8c8")

        // Board queenSideByE8A8 = blackToMove.play("e8a8");
        assertEquals(expectedFenAfterQueenSide, queenSideByOOO.toFEN())
        assertEquals(expectedFenAfterQueenSide, queenSideBy000.toFEN())
        assertEquals(expectedFenAfterQueenSide, queenSideByE8C8.toFEN())

        val kingSideByOO: Board = blackToMove.play("O-O")
        val kingSideBy00: Board = blackToMove.play("0-0")
        val kingSideByE8G8: Board = blackToMove.play("e8g8")

        // Board kingSideByE1H1 = blackToMove.play("e8h8");
        assertEquals(expectedFenAfterKingSide, kingSideByOO.toFEN())
        assertEquals(expectedFenAfterKingSide, kingSideBy00.toFEN())
        assertEquals(expectedFenAfterKingSide, kingSideByE8G8.toFEN())
    }

    @Test
    fun castling960RookSquareOccupied() {
        val initial: Board =
            Board.fromFEN("qnbbrnkr/pppppppp/8/8/8/8/PPPPPPPP/QNBBRNKR w EHeh - 0 1")
        val positionToTest: Board =
            initial.play("e4 c6 e5 e6 d4 h6 d5 b5 dxe6 Nh7 exd7 g6 dxe8=Q+ Nf8 Qxd8 Bd7 Qxd7")
        // Kingside castling shouldn't be possible, as there is a Knight on f8 where Rook should end up
        val illegal: Board = positionToTest.play("g8h8")
        assertTrue(illegal.equals(positionToTest), "Castling shouldn't be possible here")
    }

    @Test
    fun castling960RookSquareNotOccupied() {
        val initial: Board =
            Board.fromFEN("qnbbrnkr/pppppppp/8/8/8/8/PPPPPPPP/QNBBRNKR w EHeh - 0 1")
        val positionToTest: Board =
            initial.play("e4 c6 e5 e6 d4 h6 d5 b5 dxe6 Nh7 exd7 g6 dxe8=Q+ Nf8 Qxd8 Bd7 Qxd7 Ne6 c3")
        // Kingside castling should be possible, as the Knight has moved from f8
        val legal: Board = positionToTest.play("g8h8")
        assertTrue(!legal.equals(positionToTest), "Castling should be possible here")
    }

    @Test
    fun castling960With_OO_Notation() {
        val initial: Board =
            Board.fromFEN("qnbbrnkr/pppppppp/8/8/8/8/PPPPPPPP/QNBBRNKR w EHeh - 0 1")
        val positionToTest: Board =
            initial.play("e4 c6 e5 e6 d4 h6 d5 b5 dxe6 Nh7 exd7 g6 dxe8=Q+ Nf8 Qxd8 Bd7 Qxd7 Ne6 c3")
        // Kingside castling should be possible, as the Knight has moved from f8
        val legal: Board = positionToTest.play("O-O")
        assertTrue(!legal.equals(positionToTest), "Castling should be possible here")
    }

    companion object {
        val standardFen: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
    }
}
