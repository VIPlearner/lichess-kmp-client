package com.viplearner

import com.viplearner.model.PGN
import com.viplearner.chess.DefaultPGN
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readLine

object Util {

    fun pgnStream(lines: Sequence<String>): Sequence<PGN> {
        return PgnIterator(lines.iterator()).asSequence()
    }

    fun pgnStream(file: Path): Sequence<PGN> {
        return pgnStream(lines(file))
    }

    fun pgnStream(sequence: CharSequence): Sequence<PGN> {
        val lines = sequence.lineSequence()
        return pgnStream(lines)
    }

    fun lines(path: Path): Sequence<String> {
        return SystemFileSystem.source(path).buffered().use { source ->
            generateSequence { source.readLine() }
        }
    }

    /**
     * An iterator of Pgn-modelled games.
     * It lazily reads line after line of PGN data, possibly many games,
     * and assembles these lines into PGN models.
     */
    data class PgnIterator(val iterator: Iterator<String>) : Iterator<PGN> {
        private var nextPgn: PGN? = null
        private var hasNext: Boolean = true

        override fun hasNext(): Boolean {
            if (nextPgn != null) return true
            if (!hasNext) return false

            nextPgn = readNextPgn()
            if (nextPgn == null) {
                hasNext = false
                return false
            }
            return true
        }

        override fun next(): PGN {
            if (!hasNext()) throw NoSuchElementException()
            val result = nextPgn!!
            nextPgn = null
            return result
        }

        private fun readNextPgn(): PGN? {
            val tagList = mutableListOf<String>()
            var moveList = mutableListOf<String>()

            var comment = false
            var consecutiveEmptyLines = 0
            var tagsDone = false

            while (iterator.hasNext() && consecutiveEmptyLines != 2) {
                val line: String = iterator.next()
                if (!tagsDone) {
                    if (line.startsWith("[")) {
                        tagList.add(line)
                        continue
                    } else {
                        tagsDone = true
                        if (line.isBlank()) continue
                    }
                }

                moveList.add(line)

                if (line.isBlank()) {
                    if (!comment) consecutiveEmptyLines++
                    continue
                } else {
                    consecutiveEmptyLines = 0
                }

                val balance: Int = line.toCharArray().sumOf { c ->
                    when (c) {
                        '{' -> 1
                        '}' -> -1
                        else -> 0
                    }
                }

                comment = when (balance) {
                    -1 -> false
                    1 -> true
                    else -> comment
                }
            }

            if (tagList.isEmpty() && moveList.isEmpty()) return null

            val empty = moveList.reversed().takeWhile { it.isBlank() }.count()
            if (empty > 0) moveList = moveList.subList(0, moveList.size - empty)

            val moves: String = moveList.joinToString("\n")
            val tags = tagList.joinToString("\n")

            return DefaultPGN.of(tags, moves)
        }
    }


}