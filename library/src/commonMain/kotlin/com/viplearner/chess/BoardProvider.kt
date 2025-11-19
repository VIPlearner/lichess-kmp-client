package com.viplearner.chess

import chariot.internal.chess.InternalBoardProvider
import com.viplearner.model.formatted

interface BoardProvider {
    fun supportedVariants(): Set<String?>?

    fun supports(variant: String?): Boolean {
        return supportedVariants()!!.contains(variant)
    }

    fun init(variant: String): Board
    fun fromFEN(variant: String, fen: String): Board

    fun positionsByMirroredPieces(pieces: String): String {
        return "%s/%s/%s/%s/%s".formatted(
            pieces.lowercase(),
            "p".repeat(8),
            "8/8/8/8",
            "P".repeat(8),
            pieces.uppercase()
        )
    }

    companion object {
        val providers: MutableMap<String, BoardProvider>
            get() {
                val internalBoardProvider = InternalBoardProvider.provider()
                val supportedVariants = internalBoardProvider.supportedVariants()
                val map = mutableMapOf<String, BoardProvider>()

                for (variant in supportedVariants) {
                    map[variant] = internalBoardProvider
                }

                return map
            }

        fun register(supportedVariants: List<String>, provider: BoardProvider) {
            for (variant in supportedVariants) {
                providers[variant] = provider
            }
        }
        fun providers(): Map<String, BoardProvider> {
            return providers.toList().sortedWith(
                compareBy { (_, provider) -> provider is InternalBoardProvider }
            ).toMap()
        }
    }
}
