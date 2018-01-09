package com.nekonekod.tagger.taggerserver.util;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Copy from java.util.Optional(1.8), and made mutable
 */
public class MutableReference<T> {

    /**
     * If non-null, the value; if null, indicates no value is present
     */
    private T value;

    /**
     * Constructs an empty instance.
     *
     * @implNote Generally only one empty instance, {@link Optional#EMPTY},
     * should exist per VM.
     */
    private MutableReference() {
        this.value = null;
    }

    /**
     * Returns an empty {@code Optional} instance.  No value is present for this
     * Optional.
     *
     * @param <T> Type of the non-existent value
     * @return an empty {@code Optional}
     * @apiNote Though it may be tempting to do so, avoid testing if an object
     * is empty by comparing with {@code ==} against instances returned by
     * {@code Option.empty()}. There is no guarantee that it is a singleton.
     * Instead, use {@link #isPresent()}.
     */
    public static <T> MutableReference<T> empty() {
        @SuppressWarnings("unchecked")
        MutableReference<T> t = (MutableReference<T>) new MutableReference<T>();
        return t;
    }

    /**
     * Constructs an instance with the value present.
     *
     * @param value the non-null value to be present
     * @throws NullPointerException if value is null
     */
    private MutableReference(T value) {
        if (value == null)
            throw new NullPointerException();
        this.value = value;
    }

    /**
     * Returns an {@code Optional} with the specified present non-null value.
     *
     * @param <T>   the class of the value
     * @param value the value to be present, which must be non-null
     * @return an {@code Optional} with the value present
     * @throws NullPointerException if value is null
     */
    public static <T> MutableReference<T> of(T value) {
        return new MutableReference<>(value);
    }

    /**
     * Returns an {@code Optional} describing the specified value, if non-null,
     * otherwise returns an empty {@code Optional}.
     *
     * @param <T>   the class of the value
     * @param value the possibly-null value to describe
     * @return an {@code Optional} with a present value if the specified value
     * is non-null, otherwise an empty {@code Optional}
     */
    public static <T> MutableReference<T> ofNullable(T value) {
        if (value == null) return empty();
        return of(value);
    }

    /**
     * If a value is present in this {@code Optional}, returns the value,
     * otherwise throws {@code NoSuchElementException}.
     *
     * @return the non-null value held by this {@code Optional}
     * @throws NoSuchElementException if there is no value present
     * @see Optional#isPresent()
     */
    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }


    /**
     * Set value and return the old value
     *
     * @param newValue
     * @param <O>
     * @return
     */
    public <O extends T> T set(O newValue) {
        T tmp = this.value;
        this.value = newValue;
        return tmp;
    }

    /**
     * Set value and return the old value
     *
     * @param <O>
     * @return
     */
    public <O extends T> T clear() {
        return set(null);
    }

    /**
     * Return {@code true} if there is a value present, otherwise {@code false}.
     *
     * @return {@code true} if there is a value present, otherwise {@code false}
     */
    public boolean isPresent() {
        return value != null;
    }


    /**
     * If a value is present, invoke the specified consumer with the value,
     * otherwise do nothing.
     *
     * @param consumer block to be executed if a value is present
     * @throws NullPointerException if value is present and {@code consumer} is
     *                              null
     */
    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null)
            consumer.accept(value);
    }

    /**
     * Return the value if present, otherwise return {@code other}.
     *
     * @param other the value to be returned if there is no value present, may
     *              be null
     * @return the value, if present, otherwise {@code other}
     */
    public T orElse(T other) {
        return value != null ? value : other;
    }

    /**
     * Indicates whether some other object is "equal to" this Optional. The
     * other object is considered equal if:
     * <ul>
     * <li>it is also an {@code Optional} and;
     * <li>both instances have no value present or;
     * <li>the present values are "equal to" each other via {@code equals()}.
     * </ul>
     *
     * @param obj an object to be tested for equality
     * @return {code true} if the other object is "equal to" this object
     * otherwise {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof MutableReference)) {
            return false;
        }

        MutableReference<?> other = (MutableReference<?>) obj;

        if (!other.isPresent() || !this.isPresent()) {
            return false;
        }

        return Objects.equals(value, other.value);
    }

    /**
     * Returns the hash code value of the present value, if any, or 0 (zero) if
     * no value is present.
     *
     * @return hash code value of the present value or 0 if no value is present
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    /**
     * Returns a non-empty string representation of this Optional suitable for
     * debugging. The exact presentation format is unspecified and may vary
     * between implementations and versions.
     *
     * @return the string representation of this instance
     * @implSpec If a value is present the result must include its string
     * representation in the result. Empty and present Optionals must be
     * unambiguously differentiable.
     */
    @Override
    public String toString() {
        return value != null
                ? String.format("MutableReference[%s]", value)
                : "MutableReference.empty";
    }

    public static <T> Optional<T> getOrDefault(Supplier<T> supplier) {
        try {
            T t = supplier.get();
            if (t != null) {
                return Optional.of(t);
            }
        } catch (Exception e) {
            //DO NOTHING
        }
        return Optional.empty();
    }

    @FunctionalInterface
    public static interface Supplier<T> {
        T get() throws Exception;
    }

    /**
     * @param mutableReference
     * @param <T>
     * @return
     */
    public static <T> MutableReference<T> convert(MutableReference mutableReference) {
        return (MutableReference<T>) mutableReference;
    }
}
