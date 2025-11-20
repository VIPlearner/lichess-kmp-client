package com.viplearner.api.models

/**
 * Request model for creating a board seek.
 * Supports both real-time and correspondence seeks.
 */
sealed class BoardSeekRequest {
    abstract val rated: Boolean?
    abstract val variant: VariantKey?
    abstract val ratingRange: String?

    /**
     * Real-time seek request with clock time and increment.
     * The response is streamed and keeps the connection open until the seek is accepted or expires.
     *
     * @property time Clock initial time in minutes (0-180)
     * @property increment Clock increment in seconds (0-180)
     * @property color The color to play. Better left empty to automatically get 50% white
     * @property rated Whether the game is rated and impacts players ratings
     * @property variant The chess variant
     * @property ratingRange The rating range of potential opponents (e.g., "1500-1800")
     */
    data class RealTime(
        val time: Double,
        val increment: Int,
        val color: ChallengeColor? = null,
        override val rated: Boolean? = null,
        override val variant: VariantKey? = null,
        override val ratingRange: String? = null,
    ) : BoardSeekRequest() {
        init {
            require(time in 0.0..180.0) { "Time must be between 0 and 180 minutes" }
            require(increment in 0..180) { "Increment must be between 0 and 180 seconds" }
        }
    }

    /**
     * Correspondence seek request with days per turn.
     * The response completes immediately with the seek ID.
     *
     * @property days Days per turn (must be one of: 1, 2, 3, 5, 7, 10, 14)
     * @property rated Whether the game is rated and impacts players ratings
     * @property variant The chess variant
     * @property ratingRange The rating range of potential opponents (e.g., "1500-1800")
     */
    data class Correspondence(
        val days: Int,
        override val rated: Boolean? = null,
        override val variant: VariantKey? = null,
        override val ratingRange: String? = null,
    ) : BoardSeekRequest() {
        init {
            require(days in setOf(1, 2, 3, 5, 7, 10, 14)) {
                "Days must be one of: 1, 2, 3, 5, 7, 10, 14"
            }
        }
    }

    /**
     * Converts the request to form data for API submission.
     */
    fun toFormData(): Map<String, String> {
        val formData = mutableMapOf<String, String>()

        // Common parameters
        rated?.let { formData["rated"] = it.toString() }
        variant?.let { formData["variant"] = it.name.lowercase() }
        ratingRange?.let { formData["ratingRange"] = it }

        // Type-specific parameters
        when (this) {
            is RealTime -> {
                formData["time"] = time.toString()
                formData["increment"] = increment.toString()
                color?.let { formData["color"] = it.name.lowercase() }
            }
            is Correspondence -> {
                formData["days"] = days.toString()
            }
        }

        return formData
    }
}
