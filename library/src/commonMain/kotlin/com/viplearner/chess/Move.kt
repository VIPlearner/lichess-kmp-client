package com.viplearner.chess

interface Move {
    fun asString(): String

    companion object {
        fun wrap(s: String): Move {
            return object : Move {
                override fun asString(): String {
                    return s
                }
            }
        }
    }
}
