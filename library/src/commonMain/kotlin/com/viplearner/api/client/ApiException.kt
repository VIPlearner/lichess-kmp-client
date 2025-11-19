package com.viplearner.lichess.client.core

/**
 * Exception thrown when an API request fails.
 *
 * @property message The error message
 * @property status The HTTP status code, if available
 * @property body The response body, if available
 */
data class ApiException(
    override val message: String,
    val status: Int? = null,
    val body: String? = null
) : Exception(
    buildString {
        append(message)
        if (status != null) {
            append(" (HTTP $status)")
        }
        if (body != null) {
            append(": $body")
        }
    }
)

