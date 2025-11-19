package com.viplearner.chess

import com.viplearner.model.Collector
import com.viplearner.model.Collectors
import com.viplearner.model.PGN
import com.viplearner.model.SequencedMap
import com.viplearner.model.KStream
import com.viplearner.model.collect
import com.viplearner.model.filter
import com.viplearner.model.formatted
import com.viplearner.model.join
import com.viplearner.model.map
import com.viplearner.model.mapMulti
import com.viplearner.model.stream
import com.viplearner.model.toSequencedMap
import com.viplearner.model.valueOf
import dev.simplx.Character
import kotlin.jvm.JvmOverloads

class DefaultPGN @JvmOverloads constructor(
    tags: SequencedMap<String, String> = SequencedMap(),
    val text: CharSequence = ""
) : PGN {
    val tags: SequencedMap<String, String> = tags.toSequencedMap()

    override fun toString(): String {
        return if (tags.isEmpty())
            text.toString() + "\n"
        else
            tagsSection().toString() + "\n\n" + text + "\n"
    }

    override fun tags(): Map<String, String> {
        return tags
    }

    override fun tagsSection(): String {
        return tags.map({ entry ->
                """
                    [%s "%s"]
                    """.trimIndent().formatted(entry.key, entry.value)
            })
            .joinToString("\n")
    }

    override fun textSection(): String {
        return String.Companion.valueOf(text)
    }

    override fun withTags(_tags: Map<String, String>): DefaultPGN {
        return DefaultPGN(
            _tags.entries.stream().collect(DefaultPGN.Companion.sequencedMap), text
        )
    }

    override fun withTags(_tags: UnaryOperator<KStream<Map.Entry<String, String>>>): DefaultPGN {
        return DefaultPGN(
            _tags.apply(tags.entries.stream())
                .collect(DefaultPGN.Companion.sequencedMap),
            text
        )
    }

    override fun withText(_text: CharSequence): PGN {
        return DefaultPGN(tags, _text)
    }


    override fun filterTags(filter: BiPredicate<String, String>): PGN {
        return withTags({ stream ->
            stream
                .filter({ entry -> filter.test(entry.key, entry.value) })
        })
    }

//    override fun replaceTags(replace: BiFunction<String, String, String>): PGN {
//        return withTags({ stream ->
//            stream
//                .map({ entry -> entry.apply {
//value = replace.apply(key, value)
//                } })
//        })
//    }

    override fun addTags(add: Map<String, String>): PGN {
        return withTags({ s -> KStream.Companion.concat(s, add.entries.stream()) })
    }

    companion object {
        /**
         *     public static PGN of(String tagsSection, String text) {
         *         return new DefaultPGN(tagsSection.lines()
         *                 .<Map.Entry<String,String>>mapMulti((line, downstream) -> { try {
         *                     downstream.accept(Map.entry(
         *                                 line.substring(1, line.indexOf(' ')),
         *                                 line.substring(line.indexOf('"')+1, line.length()-2)));
         *                 } catch (Exception e) {}
         *                 }).collect(sequencedMap),
         *                 text);
         *     }
         */
        fun of(tagsSection: String, text: String?): PGN {
            val tags = tagsSection.lines().stream()
                .mapMulti({ line, downstream ->
                    try {
                        downstream.accept(
                            object: Map.Entry<String, String>{
                                override val key: String
                                    get() = line.substring(1, line.indexOf(' '))
                                override val value: String
                                    get() = line.substring(line.indexOf('"') + 1, line.length - 2)
                    }
                        )
                    } catch (e: Exception) {
                    }
                })
                .collect(sequencedMap)
            return DefaultPGN(tags, text ?: "")
        }

        private val sequencedMap: Collector<Map.Entry<String, String>, *, SequencedMap<String, String>> =
            Collectors.toMap(
                { it.key },
                { it.value },
                { _, newValue -> newValue },
                { LinkedHashMap() }
            )

        fun render(text: PGN.Text): String {
            return when (text) {
                is PGN.Text.Move -> if (text.num.move == 0) text.san else "%d%s %s".formatted(
                    text.num.move,
                    ".".repeat(text.num.dots),
                    text.san
                )

                is PGN.Text.Variation -> "(%s)".formatted(
                    String.Companion.join(
                        " ",
                        text.variation.stream().map(PGN.Text.Companion::render).toList()
                    )
                )

                is PGN.Text.Result -> text.result
                is PGN.Text.Empty -> ""
                is PGN.Text.Comment -> "{%s}".formatted(text.comment)
                else -> throw IllegalStateException("Unknown PGN.Text type")
            }
        }

        fun parse(moves: String): KStream<PGN.Text> {
            val moves = moves.trim()
            return when {
                moves == "" -> {
                    KStream.Companion.of(PGN.Text.Empty())
                }

                result(moves) != null -> {
                    KStream.Companion.of(result(moves)!!)
                }
                Character.isDigit(moves[0]) -> {
                    val dotPos = moves.indexOf(".")
                    val moveNum = moves.substring(0, dotPos).toInt()
                    val dots = if (moves[dotPos + 1] == '.') 3 else 1
                    var sanBegin = moves.indexOf(" ", dotPos + 1)
                    while (moves[sanBegin].isWhitespace()) sanBegin++
                    val sanEnd = indexOfOrEnd(" ", sanBegin, moves)
                    val san = moves.substring(sanBegin, sanEnd)
                    KStream.Companion.concat(
                        KStream.Companion.of(PGN.Text.Move(san, moveNum, dots)),
                        parse(moves.substring(sanEnd))
                    )
                }
                moves[0] == '(' -> {
                    var inComment = false
                    var nest = 1
                    var pos = 1
                    while (nest != 0) {
                        inComment = when (moves[pos]) {
                            '{' -> true
                            '}' -> false
                            else -> inComment
                        }
                        if (!inComment) {
                            nest = when (moves[pos]) {
                                '(' -> nest + 1
                                ')' -> nest - 1
                                else -> nest
                            }
                        }
                        pos++
                    }
                    KStream.Companion.concat(
                        KStream.Companion.of(
                            PGN.Text.Variation(
                                parse(moves.substring(1, pos - 1)).toList()
                            )
                        ),
                        parse(moves.substring(pos))
                    )
                }
                moves[0] == '{' -> KStream.Companion.concat(
                    KStream.Companion.of(PGN.Text.Comment(moves.substring(1, moves.indexOf('}')))),
                    parse(moves.substring(moves.indexOf('}') + 1))
                )
                else -> KStream.Companion.concat(
                    KStream.Companion.of(PGN.Text.Move(moves.substring(0, indexOfOrEnd(" ", 0, moves)))),
                    parse(moves.substring(indexOfOrEnd(" ", 0, moves)))
                )

            }
        }

        fun result(s: String): PGN.Text.Result? {
            return when (s) {
                "*", "1-0", "0-1", "1/2-1/2" -> PGN.Text.Result(s)
                else -> null
            }
        }

        private fun indexOfOrEnd(target: String?, from: Int, source: String): Int {
            val index = source.indexOf(target!!, from)
            return if (index == -1) source.length else index
        }
    }
}