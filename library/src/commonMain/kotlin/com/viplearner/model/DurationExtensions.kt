package com.viplearner.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.microseconds
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds

/**
 * Extension functions for kotlin.time.Duration to provide compatibility with java.time.Duration API.
 * This allows the codebase to remain KMP-compatible while maintaining the same API surface.
 */

// ============================================
// Static Factory Methods (Companion Extensions)
// ============================================

/**
 * Obtains a Duration representing a number of nanoseconds.
 */
fun Duration.Companion.ofNanos(nanos: Long): Duration = nanos.nanoseconds

/**
 * Obtains a Duration representing a number of microseconds.
 */
fun Duration.Companion.ofMicros(micros: Long): Duration = micros.microseconds

/**
 * Obtains a Duration representing a number of milliseconds.
 */
fun Duration.Companion.ofMillis(millis: Long): Duration = millis.milliseconds

/**
 * Obtains a Duration representing a number of seconds.
 */
fun Duration.Companion.ofSeconds(seconds: Long): Duration = seconds.seconds

fun Duration.Companion.ofSeconds(seconds: Int): Duration = seconds.seconds

/**
 * Obtains a Duration representing a number of seconds with nanosecond adjustment.
 */
fun Duration.Companion.ofSeconds(seconds: Long, nanoAdjustment: Long): Duration =
    seconds.seconds + nanoAdjustment.nanoseconds

/**
 * Obtains a Duration representing a number of minutes.
 */
fun Duration.Companion.ofMinutes(minutes: Long): Duration = minutes.minutes

fun Duration.Companion.ofMinutes(minutes: Int): Duration = minutes.minutes

/**
 * Obtains a Duration representing a number of hours.
 */
fun Duration.Companion.ofHours(hours: Long): Duration = hours.hours

/**
 * Obtains a Duration representing a number of days.
 */
fun Duration.Companion.ofDays(days: Long): Duration = days.days

/**
 * Obtains a Duration representing zero duration.
 */
fun Duration.Companion.ofZero(): Duration = Duration.ZERO

// ============================================
// Instance Methods (Regular Extensions)
// ============================================

/**
 * Gets the number of seconds in this duration.
 */
fun Duration.toSeconds(): Long = this.inWholeSeconds

/**
 * Converts this duration to the total length in milliseconds.
 */
fun Duration.toMillis(): Long = this.inWholeMilliseconds

/**
 * Gets the number of minutes in this duration.
 */
fun Duration.toMinutes(): Long = this.inWholeMinutes

/**
 * Gets the number of hours in this duration.
 */
fun Duration.toHours(): Long = this.inWholeHours

/**
 * Gets the number of days in this duration.
 */
fun Duration.toDays(): Long = this.inWholeDays

/**
 * Extracts the number of days in the duration.
 */
fun Duration.toDaysPart(): Long = this.inWholeDays

/**
 * Extracts the number of hours part in the duration.
 */
fun Duration.toHoursPart(): Int = (this.inWholeHours % 24).toInt()

/**
 * Extracts the number of minutes part in the duration.
 */
fun Duration.toMinutesPart(): Int = (this.inWholeMinutes % 60).toInt()

/**
 * Extracts the number of seconds part in the duration.
 */
fun Duration.toSecondsPart(): Int = (this.inWholeSeconds % 60).toInt()

/**
 * Extracts the number of milliseconds part in the duration.
 */
fun Duration.toMillisPart(): Int = (this.inWholeMilliseconds % 1000).toInt()

/**
 * Extracts the number of nanoseconds part in the duration.
 */
fun Duration.toNanosPart(): Int = (this.inWholeNanoseconds % 1_000_000_000).toInt()

/**
 * Returns a copy of this duration with the specified duration added.
 */
fun Duration.plus(duration: Duration): Duration = this + duration

/**
 * Returns a copy of this duration with the specified duration added.
 */
fun Duration.plusSeconds(seconds: Long): Duration = this + seconds.seconds

/**
 * Returns a copy of this duration with the specified duration added.
 */
fun Duration.plusMinutes(minutes: Long): Duration = this + minutes.minutes

/**
 * Returns a copy of this duration with the specified duration added.
 */
fun Duration.plusHours(hours: Long): Duration = this + hours.hours

/**
 * Returns a copy of this duration with the specified duration added.
 */
fun Duration.plusDays(days: Long): Duration = this + days.days

/**
 * Returns a copy of this duration with the specified duration added.
 */
fun Duration.plusMillis(millis: Long): Duration = this + millis.milliseconds

/**
 * Returns a copy of this duration with the specified duration added.
 */
fun Duration.plusNanos(nanos: Long): Duration = this + nanos.nanoseconds

/**
 * Returns a copy of this duration with the specified duration subtracted.
 */
fun Duration.minus(duration: Duration): Duration = this - duration

/**
 * Returns a copy of this duration with the specified duration subtracted.
 */
fun Duration.minusSeconds(seconds: Long): Duration = this - seconds.seconds

/**
 * Returns a copy of this duration with the specified duration subtracted.
 */
fun Duration.minusMinutes(minutes: Long): Duration = this - minutes.minutes

/**
 * Returns a copy of this duration with the specified duration subtracted.
 */
fun Duration.minusHours(hours: Long): Duration = this - hours.hours

/**
 * Returns a copy of this duration with the specified duration subtracted.
 */
fun Duration.minusDays(days: Long): Duration = this - days.days

/**
 * Returns a copy of this duration with the specified duration subtracted.
 */
fun Duration.minusMillis(millis: Long): Duration = this - millis.milliseconds

/**
 * Returns a copy of this duration with the specified duration subtracted.
 */
fun Duration.minusNanos(nanos: Long): Duration = this - nanos.nanoseconds

/**
 * Returns a copy of this duration multiplied by the scalar.
 */
fun Duration.multipliedBy(multiplicand: Long): Duration = this * multiplicand.toInt()

/**
 * Returns a copy of this duration divided by the specified value.
 */
fun Duration.dividedBy(divisor: Long): Duration = this / divisor.toInt()

/**
 * Returns a copy of this duration with the length negated.
 */
fun Duration.negated(): Duration = -this

/**
 * Returns a copy of this duration with a positive length.
 */
fun Duration.abs(): Duration = this.absoluteValue

/**
 * Checks if this duration is zero length.
 */
fun Duration.isZero(): Boolean = this == Duration.ZERO

/**
 * Checks if this duration is negative, excluding zero.
 */
fun Duration.isNegative(): Boolean = this.isNegative()

/**
 * Compares this duration to the specified duration.
 */
fun Duration.compareTo(other: Duration): Int = this.compareTo(other)

