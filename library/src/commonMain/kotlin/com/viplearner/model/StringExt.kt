package com.viplearner.model

import dev.simplx.KotlinFormatter
import kotlinx.coroutines.NonCancellable.join

fun String.Companion.valueOf(obj: Any?): String {
    return obj?.toString() ?: "null"
}

fun String.formatted(vararg args: Any?): String {
    return KotlinFormatter.format(this, *args)
}
fun String.Companion.join(delimiter: CharSequence, elements: Iterable<CharSequence?>): String {
    val sb = StringBuilder()
    val iterator = elements.iterator()
    while (iterator.hasNext()) {
        sb.append(iterator.next())
        if (iterator.hasNext()) {
            sb.append(delimiter)
        }
    }
    return sb.toString()
}


fun String.Companion.join(delimiter: CharSequence, vararg elements: CharSequence?): String {
    return join(delimiter, elements.asList())
}


