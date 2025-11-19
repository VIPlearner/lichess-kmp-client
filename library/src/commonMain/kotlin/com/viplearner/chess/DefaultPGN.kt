package com.viplearner.chess

import com.viplearner.model.PGN
import com.viplearner.model.formatted
import com.viplearner.model.valueOf
import dev.simplx.Character
import kotlin.jvm.JvmOverloads

class DefaultPGN @JvmOverloads constructor(
    tags: Map<String, String> = linkedMapOf(),
    val text: CharSequence = ""
) : PGN {
    val tags: Map<String, String> = LinkedHashMap(tags)

    override fun toString(): String {
        return if (tags.isEmpty())
            text.toString() + "\n"
        else
            tagsSection() + "\n\n" + text + "\n"
    }

    override fun tags(): Map<String, String> {
        return tags
    }

    override fun tagsSection(): String {
        return tags.map { (key, value) ->
            """[%s "%s"]""".formatted(key, value)
        }.joinToString("\n")
    }

    override fun textSection(): String {
        return String.Companion.valueOf(text)
    }

    override fun withTags(_tags: Map<String, String>): DefaultPGN {
        return DefaultPGN(LinkedHashMap(_tags), text)
    }

    override fun withTags(_tags: (Sequence<Map.Entry<String, String>>) -> Sequence<Map.Entry<String, String>>): DefaultPGN {
        val newTags = _tags(tags.entries.asSequence())
            .associate { it.key to it.value }
        return DefaultPGN(LinkedHashMap(newTags), text)
    }

    override fun withText(_text: CharSequence): PGN {
        return DefaultPGN(tags, _text)
    }


    override fun filterTags(filter: (String, String) -> Boolean): PGN {
        return withTags { entries: Sequence<Map.Entry<String, String>> ->
            entries.filter { entry -> filter(entry.key, entry.value) }
        }
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
        return withTags { s: Sequence<Map.Entry<String, String>> -> s + add.entries.asSequence() }
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
            val tags = tagsSection.lines()
                .mapNotNull { line ->
                    try {
                        val key = line.substring(1, line.indexOf(' '))
                        val value = line.substring(line.indexOf('"') + 1, line.length - 2)
                        key to value
                    } catch (e: Exception) {
                        null
                    }
                }
                .toMap(LinkedHashMap())
            return DefaultPGN(tags, text ?: "")
        }


        fun render(text: PGN.Text): String {
            return when (text) {
                is PGN.Text.Move -> if (text.num.move == 0) text.san else "%d%s %s".formatted(
                    text.num.move,
                    ".".repeat(text.num.dots),
                    text.san
                )

                is PGN.Text.Variation -> "(%s)".formatted(
                    text.variation.joinToString(" ") { PGN.Text.render(it) }
                )

                is PGN.Text.Result -> text.result
                is PGN.Text.Empty -> ""
                is PGN.Text.Comment -> "{%s}".formatted(text.comment)
                else -> throw IllegalStateException("Unknown PGN.Text type")
            }
        }

        fun parse(moves: String): Sequence<PGN.Text> {
            val trimmedMoves = moves.trim()
            return when {
                trimmedMoves == "" -> {
                    sequenceOf(PGN.Text.Empty())
                }

                result(trimmedMoves) != null -> {
                    sequenceOf(result(trimmedMoves)!!)
                }
                Character.isDigit(trimmedMoves[0]) -> {
                    val dotPos = trimmedMoves.indexOf(".")
                    val moveNum = trimmedMoves.substring(0, dotPos).toInt()
                    val dots = if (trimmedMoves[dotPos + 1] == '.') 3 else 1
                    var sanBegin = trimmedMoves.indexOf(" ", dotPos + 1)
                    while (trimmedMoves[sanBegin].isWhitespace()) sanBegin++
                    val sanEnd = indexOfOrEnd(" ", sanBegin, trimmedMoves)
                    val san = trimmedMoves.substring(sanBegin, sanEnd)
                    sequenceOf(PGN.Text.Move(san, moveNum, dots)) + parse(trimmedMoves.substring(sanEnd))
                }
                trimmedMoves[0] == '(' -> {
                    var inComment = false
                    var nest = 1
                    var pos = 1
                    while (nest != 0) {
                        inComment = when (trimmedMoves[pos]) {
                            '{' -> true
                            '}' -> false
                            else -> inComment
                        }
                        if (!inComment) {
                            nest = when (trimmedMoves[pos]) {
                                '(' -> nest + 1
                                ')' -> nest - 1
                                else -> nest
                            }
                        }
                        pos++
                    }
                    sequenceOf(
                        PGN.Text.Variation(
                            parse(trimmedMoves.substring(1, pos - 1)).toList()
                        )
                    ) + parse(trimmedMoves.substring(pos))
                }
                trimmedMoves[0] == '{' -> sequenceOf(
                    PGN.Text.Comment(trimmedMoves.substring(1, trimmedMoves.indexOf('}')))
                ) + parse(trimmedMoves.substring(trimmedMoves.indexOf('}') + 1))
                else -> sequenceOf(
                    PGN.Text.Move(trimmedMoves.substring(0, indexOfOrEnd(" ", 0, trimmedMoves)))
                ) + parse(trimmedMoves.substring(indexOfOrEnd(" ", 0, trimmedMoves)))

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