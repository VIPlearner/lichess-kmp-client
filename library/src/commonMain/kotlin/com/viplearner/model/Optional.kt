package com.viplearner.model

/**
 * A multiplatform wrapper for Optional that provides compatibility with java.util.Optional API.
 * This allows the codebase to remain KMP-compatible while maintaining the same API surface.
 */
class Optional<T> private constructor(private val value: T?) {

    companion object {
        private val EMPTY = Optional<Any?>(null)

        /**
         * Returns an empty Optional instance.
         */
        @Suppress("UNCHECKED_CAST")
        fun <T> empty(): Optional<T> {
            return EMPTY as Optional<T>
        }

        /**
         * Returns an Optional describing the given non-null value.
         * @throws NullPointerException if value is null
         */
        fun <T> of(value: T): Optional<T> {
            if (value == null) {
                throw NullPointerException("value must not be null")
            }
            return Optional(value)
        }

        /**
         * Returns an Optional describing the given value, if non-null, otherwise returns an empty Optional.
         */
        fun <T> ofNullable(value: T?): Optional<T> {
            return if (value == null) empty() else of(value)
        }
    }

    /**
     * If a value is present, returns the value, otherwise throws NoSuchElementException.
     */
    fun get(): T {
        if (value == null) {
            throw NoSuchElementException("No value present")
        }
        return value
    }

    /**
     * Returns true if a value is present, otherwise false.
     */
    fun isPresent(): Boolean {
        return value != null
    }

    /**
     * Returns true if a value is not present, otherwise false.
     */
    fun isEmpty(): Boolean {
        return value == null
    }

    /**
     * If a value is present, performs the given action with the value, otherwise does nothing.
     */
    fun ifPresent(action: (T) -> Unit) {
        if (value != null) {
            action(value)
        }
    }

    /**
     * If a value is present, performs the given action with the value, otherwise performs the empty action.
     */
    fun ifPresentOrElse(action: (T) -> Unit, emptyAction: () -> Unit) {
        if (value != null) {
            action(value)
        } else {
            emptyAction()
        }
    }

    /**
     * If a value is present, returns an Optional describing the result of applying the given mapping function to the value,
     * otherwise returns an empty Optional.
     */
    fun <U> map(mapper: (T) -> U?): Optional<U> {
        return if (value == null) {
            empty()
        } else {
            ofNullable(mapper(value))
        }
    }

    /**
     * If a value is present, returns the result of applying the given Optional-bearing mapping function to the value,
     * otherwise returns an empty Optional.
     */
    fun <U> flatMap(mapper: (T) -> Optional<U>): Optional<U> {
        return if (value == null) {
            empty()
        } else {
            mapper(value)
        }
    }

    /**
     * If a value is present, returns an Optional describing the value, if the value matches the given predicate,
     * otherwise returns an empty Optional.
     */
    fun filter(predicate: (T) -> Boolean): Optional<T> {
        return if (value == null) {
            this
        } else {
            if (predicate(value)) this else empty()
        }
    }

    /**
     * If a value is present, returns the value, otherwise returns other.
     */
    fun orElse(other: T?): T? {
        return value ?: other
    }

    /**
     * If a value is present, returns the value, otherwise returns the result produced by the supplying function.
     */
    fun orElseGet(supplier: () -> T): T {
        return value ?: supplier()
    }

    /**
     * If a value is present, returns the value, otherwise throws NoSuchElementException.
     */
    fun orElseThrow(): T {
        if (value == null) {
            throw NoSuchElementException("No value present")
        }
        return value
    }

    /**
     * If a value is present, returns the value, otherwise throws the exception produced by the exception supplying function.
     */
    fun <X : Throwable> orElseThrow(exceptionSupplier: () -> X): T {
        if (value == null) {
            throw exceptionSupplier()
        }
        return value
    }

    /**
     * If a value is present, returns an Optional containing the value, otherwise returns the Optional produced by the supplying function.
     */
    fun or(supplier: () -> Optional<T>): Optional<T> {
        return if (value != null) this else supplier()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Optional<*>

        return value == other.value
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

    override fun toString(): String {
        return if (value != null) {
            "Optional[$value]"
        } else {
            "Optional.empty"
        }
    }
}

