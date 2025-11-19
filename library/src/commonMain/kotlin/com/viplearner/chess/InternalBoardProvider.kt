package com.viplearner.chess

class InternalBoardProvider : Chess960BoardProvider {
    override fun supportedVariants(): Set<String> {
        return setOf(
            "standard",
            "chess960",
            "fromPosition"
        )
    }

    override fun init(variant: String): Board {
        return when (variant) {
            "standard" -> fromFEN(variant, FEN.standard.toString())
            "chess960" -> random960()
            "fromPosition" -> fromFEN(variant, FEN.standard.toString())
            else -> throw IllegalArgumentException("Unsupported variant: $variant")
        }
    }

    override fun fromFEN(variant: String, fen: String): Board {
        return when (variant) {
            "standard" -> NaiveChess.of(variant, fen)
            "chess960" -> NaiveChess.of(variant, fen)
            "fromPosition" -> NaiveChess.of(variant, fen)
            else -> throw IllegalArgumentException("Unsupported variant: $variant")
        }
    }

    companion object {
        private val instance: InternalBoardProvider = InternalBoardProvider()
        fun provider(): InternalBoardProvider {
            return instance
        }
    }
}
