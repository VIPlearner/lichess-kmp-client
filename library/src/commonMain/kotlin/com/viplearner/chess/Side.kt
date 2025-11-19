package com.viplearner.chess

enum class Side {
    white, black;

    fun other(): Side {
        return when (this) {
            Side.white -> Side.black
            Side.black -> Side.white
        }
    }
}
