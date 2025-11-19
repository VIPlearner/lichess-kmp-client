package com.viplearner.model

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

/**
 * A multiplatform wrapper for ZonedDateTime that provides compatibility with java.time.ZonedDateTime API.
 * This allows the codebase to remain KMP-compatible while maintaining the same API surface.
 */
@OptIn(ExperimentalTime::class)
class ZonedDateTime private constructor(private val instant: kotlin.time.Instant, private val zone: TimeZone) {

    companion object {
        /**
         * Returns the current date-time from the system clock in the default time-zone.
         */
        fun now(): ZonedDateTime {
            return ZonedDateTime(kotlin.time.Clock.System.now(), TimeZone.currentSystemDefault())
        }

        /**
         * Returns the current date-time from the system clock in the specified time-zone.
         */
        fun now(zone: TimeZone): ZonedDateTime {
            return ZonedDateTime(kotlin.time.Clock.System.now(), zone)
        }

        /**
         * Creates a ZonedDateTime from an Instant and TimeZone.
         */
        fun ofInstant(instant: kotlin.time.Instant, zone: TimeZone): ZonedDateTime {
            return ZonedDateTime(instant, zone)
        }

        /**
         * Creates a ZonedDateTime from epoch milliseconds.
         */
        fun ofEpochMilli(epochMilli: Long, zone: TimeZone = TimeZone.currentSystemDefault()): ZonedDateTime {
            return ZonedDateTime(kotlin.time.Instant.fromEpochMilliseconds(epochMilli), zone)
        }

        /**
         * Creates a ZonedDateTime from epoch seconds.
         */
        fun ofEpochSecond(epochSecond: Long, zone: TimeZone = TimeZone.currentSystemDefault()): ZonedDateTime {
            return ZonedDateTime(kotlin.time.Instant.fromEpochSeconds(epochSecond), zone)
        }
    }

    /**
     * withNano
     */
    fun withNano(nanoOfSecond: Int): ZonedDateTime {
        val epochMilli = instant.toEpochMilliseconds()
        val currentNano = (epochMilli % 1000) * 1_000_000
        val deltaNanos = nanoOfSecond - currentNano
        val newEpochMilli = epochMilli + deltaNanos / 1_000_000
        return ZonedDateTime(kotlin.time.Instant.fromEpochMilliseconds(newEpochMilli), zone)
    }

    /**
     * Converts this date-time to an Instant.
     */
    fun toInstant(): kotlin.time.Instant {
        return instant
    }

    /**
     * Returns the time zone.
     */
    fun getZone(): TimeZone {
        return zone
    }

    /**
     * Returns a copy of this ZonedDateTime with the specified number of minutes added.
     */
    fun plusMinutes(minutes: Long): ZonedDateTime {
        val newInstant = kotlin.time.Instant.fromEpochMilliseconds(instant.toEpochMilliseconds() + minutes * 60 * 1000)
        return ZonedDateTime(newInstant, zone)
    }

    /**
     * Returns a copy of this ZonedDateTime with the specified number of hours added.
     */
    fun plusHours(hours: Long): ZonedDateTime {
        val newInstant = kotlin.time.Instant.fromEpochMilliseconds(instant.toEpochMilliseconds() + hours * 60 * 60 * 1000)
        return ZonedDateTime(newInstant, zone)
    }

    /**
     * Returns a copy of this ZonedDateTime with the specified number of days added.
     */
    fun plusDays(days: Long): ZonedDateTime {
        val newInstant = kotlin.time.Instant.fromEpochMilliseconds(instant.toEpochMilliseconds() + days * 24 * 60 * 60 * 1000)
        return ZonedDateTime(newInstant, zone)
    }

    /**
     * Returns a copy of this ZonedDateTime with the specified number of seconds added.
     */
    fun plusSeconds(seconds: Long): ZonedDateTime {
        val newInstant = kotlin.time.Instant.fromEpochMilliseconds(instant.toEpochMilliseconds() + seconds * 1000)
        return ZonedDateTime(newInstant, zone)
    }

    /**
     * Returns a copy of this ZonedDateTime with the specified number of minutes subtracted.
     */
    fun minusMinutes(minutes: Long): ZonedDateTime {
        return plusMinutes(-minutes)
    }

    /**
     * Returns a copy of this ZonedDateTime with the specified number of hours subtracted.
     */
    fun minusHours(hours: Long): ZonedDateTime {
        return plusHours(-hours)
    }

    /**
     * Returns a copy of this ZonedDateTime with the specified number of days subtracted.
     */
    fun minusDays(days: Long): ZonedDateTime {
        return plusDays(-days)
    }

    /**
     * Returns a copy of this ZonedDateTime with the specified number of seconds subtracted.
     */
    fun minusSeconds(seconds: Long): ZonedDateTime {
        return plusSeconds(-seconds)
    }

    /**
     * Returns a copy of this ZonedDateTime with a different time-zone, retaining the instant.
     */
    fun withZoneSameInstant(zone: TimeZone): ZonedDateTime {
        return ZonedDateTime(instant, zone)
    }

    /**
     * Checks if this date-time is after the specified date-time.
     */
    fun isAfter(other: ZonedDateTime): Boolean {
        return instant > other.instant
    }

    /**
     * Checks if this date-time is before the specified date-time.
     */
    fun isBefore(other: ZonedDateTime): Boolean {
        return instant < other.instant
    }

    /**
     * Checks if this date-time is equal to the specified date-time.
     */
    fun isEqual(other: ZonedDateTime): Boolean {
        return instant == other.instant
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ZonedDateTime

        if (instant != other.instant) return false
        if (zone != other.zone) return false

        return true
    }

    override fun hashCode(): Int {
        var result = instant.hashCode()
        result = 31 * result + zone.hashCode()
        return result
    }

    override fun toString(): String {
        val localDateTime = instant.toLocalDateTime(zone)
        return "${localDateTime}[${zone}]"
    }
}

/**
 * Extension function to convert kotlinx.datetime.Instant to epoch milliseconds.
 * Provides API compatibility with java.time.Instant.
 */
@OptIn(ExperimentalTime::class)
fun kotlin.time.Instant.toEpochMilli(): Long = this.toEpochMilliseconds()

@OptIn(ExperimentalTime::class)
fun kotlin.time.Instant.Companion.ofEpochMilli(epochMilli: Long): kotlin.time.Instant =
    kotlin.time.Instant.fromEpochMilliseconds(epochMilli)

/**
 * Extension function to convert kotlinx.datetime.Instant to epoch seconds.
 * Provides API compatibility with java.time.Instant.
 */
@OptIn(ExperimentalTime::class)
fun kotlin.time.Instant.getEpochSecond(): Long = this.epochSeconds

