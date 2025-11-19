package com.viplearner

import com.viplearner.model.KotlinStream
import com.viplearner.model.PGN
import com.viplearner.model.KStream
import com.viplearner.model.join
import com.viplearner.model.stream as kStream
import com.viplearner.chess.DefaultPGN
import com.viplearner.model.Spliterator
import com.viplearner.model.Spliterator.Companion.ORDERED
import com.viplearner.model.StreamSupport
import com.viplearner.model.stream
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readLine
import kotlin.jvm.JvmRecord

object Util {

    fun pgnStream(kStream: KStream<String>): KStream<PGN> {
        return StreamSupport.stream(PgnSpliterator(kStream.iterator()), false)
            .onClose(kStream::close)
    }

    fun pgnStream(file: Path): KStream<PGN> {
        return pgnStream(lines(file))
    }

    fun pgnStream(sequence: CharSequence): KStream<PGN> {
        val lines = KotlinStream(sequence.lineSequence())
        return pgnStream(lines)
    }


    fun lines(path: Path): KStream<String> {
        val kxPath = path
        return SystemFileSystem.source(kxPath).buffered().use { source ->
            generateSequence { source.readLine() }.toList()
        }.kStream()
    }


    /**
     * An iterator of Pgn-modelled games.
     * It lazily reads line after line of PGN data, possibly many games,
     * and assembles these lines into PGN models.
     */
    @JvmRecord
    data class PgnSpliterator(val iterator: Iterator<String>) : Spliterator<PGN> {
        override fun tryAdvance(action: Consumer<PGN>): Boolean {
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

                val balance: Int = line.toCharArray().sumOf(
                    { c ->
                        when (c) {
                            '{' -> 1
                            '}' -> -1
                            else -> 0
                        }
                    },
                )

                comment = when (balance) {
                    -1 -> false
                    1 -> true
                    else -> comment
                }
            }

            if (tagList.isEmpty() && moveList!!.isEmpty()) return false

            val empty = moveList!!.reversed().kStream().takeWhile(String::isBlank).count() as Int
            if (empty > 0) moveList = moveList.subList(0, moveList.size - empty)

            val moves: String? = String.join("\n", moveList)
            val tags = String.join("\n", tagList)
            action.accept(DefaultPGN.of(tags, moves))

            return true
        }

        override fun trySplit(): Spliterator<PGN>? {
            return null
        }

        override fun estimateSize(): Long {
            return Long.MAX_VALUE
        }

        override fun characteristics(): Int {
            return ORDERED
        }
    }



}