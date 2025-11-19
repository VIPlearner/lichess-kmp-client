package com.viplearner.model

import com.viplearner.Util
import com.viplearner.chess.DefaultPGN
import kotlinx.io.files.Path
import kotlin.math.max

interface PGN {
    /** Retrieves a mapping of PGN tag names to values */
    fun tags(): Map<String, String>

    /** Retrieves the list of moves from the mainline of the PGN (omitting comments, variations and result) */
    fun movesList(): List<String> {
        return textList().stream()
            .mapMulti({ text, mapper ->
            if (text is Text.Move) mapper.accept(text.san)
        }).toList()
    }

    /** Retrieves a string of moves from the mainline of the PGN (omitting comments, variations and result) */
    fun moves(): String {
        return String.join(" ", movesList())
    }

    fun textList(): List<Text> {
        return com.viplearner.model.PGN.Text.Companion.parse(textSection()).toList()
    }

    /** Retrieves a string of the PGN tags */
    fun tagsSection(): String

    /** Retrieves a string of the PGN move text */
    fun textSection(): String

    /** A copy of this `PGN` with tags replaced with `tags` */
    fun withTags(tags: Map<String, String>): PGN

    /** A copy of this `PGN` with move text replaced with `text` */
    fun withText(text: CharSequence): PGN

    /** @return a copy of this `PGN` with tags yielded from applying provided `tags` operator on existing tags.
     */
    fun withTags(tags: UnaryOperator<KStream<Map.Entry<String, String>>>): PGN

    /** @return a copy of this `PGN` with tags yielded from applying provided `filter` on existing tags.
     */
    fun filterTags(filter: BiPredicate<String, String>): PGN

//    /** @return a copy of this `PGN` with tags yielded from applying provided `mapper` on existing tags.
//     */
//    fun replaceTags(mapper: BiFunction<String, String, String>): PGN

    /** @return a copy of this `PGN` with tags yielded from adding (overwrites duplicates) `tags` to existing tags.
     */
    fun addTags(tags: Map<String, String>): PGN

    interface Text {
        class Move(san: String, num: Num) : Text {
            constructor(san: String) : this(san, 0, 0)
            constructor(san: String, move: Int, dots: Int) : this(san, com.viplearner.model.PGN.Text.Num(move, dots))

            val san: String
            val num: Num

            init {
                var san = san
                var num = num
                san = requireNotNull(san)
                num = requireNotNull(num)
                this.san = san
                this.num = num
            }
        }

        class Variation(variation: List<Text>) : Text {
            val variation: List<Text>

            init {
                var variation = variation
                variation = requireNotNull(variation).toList()
                this.variation = variation
            }
        }

        class Comment(comment: String) : Text {
            val comment: String

            init {
                var comment = comment
                comment = requireNotNull(comment)
                this.comment = comment
            }
        }

        class Result(result: String) : Text {
            val result: String

            init {
                var result = result
                result = requireNotNull(result)
                this.result = result
            }
        }

        class Empty : Text

        class Num(move: Int, dots: Int) {
            val move: Int
            val dots: Int

            init {
                var move = move
                var dots = dots
                move = max(0, move)
                dots = dots.coerceIn(0,3)
                this.move = move
                this.dots = dots
            }
        }

        companion object {
            fun render(text: Text): String {
                return DefaultPGN.render(text)
            }

            fun parse(moves: String): KStream<Text> {
                return DefaultPGN.parse(moves)
            }
        }
    }

    companion object {
        /** Parses a string of PGN into a `PGN` */
        fun read(sequence: CharSequence): PGN {
            com.viplearner.model.PGN.Companion.stream(sequence).use { stream ->
                return stream.findFirst().orElseGet({ DefaultPGN() })
            }
        }

        /** Parses a file of PGN into a `PGN` */
        fun read(file: Path): PGN {
            com.viplearner.model.PGN.Companion.stream(file).use { stream ->
                return stream.findFirst().orElseGet({ DefaultPGN() })
            }
        }

        /** Parses a string of PGNs into a `Stream<PGN>`</PGN> */
        fun stream(sequence: CharSequence): KStream<PGN> {
            return Util.pgnStream(sequence)
        }

        /** Parses a file of PGNs into a `Stream<PGN>`</PGN> */
        fun stream(file: Path): KStream<PGN> {
            return Util.pgnStream(file)
        }
    }
}
