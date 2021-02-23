package com.simsilica.lemur;

import java.util.Iterator;

public class Iterators {

    /**
     * Advances {@code iterator} {@code position + 1} times, returning the element at the {@code
     * position}th position.
     *
     * @param position position of the element to return
     * @return the element at the specified position in {@code iterator}
     * @throws IndexOutOfBoundsException if {@code position} is negative or greater than or equal to
     *                                   the number of elements remaining in {@code iterator}
     */
    public static <T> T get(Iterator<T> iterator, int position) {
        checkNonnegative(position);
        int skipped = advance(iterator, position);
        if (!iterator.hasNext()) {
            throw new IndexOutOfBoundsException(
                    "position ("
                            + position
                            + ") must be less than the number of elements that remained ("
                            + skipped
                            + ")");
        }
        return iterator.next();
    }

    /**
     * Advances {@code iterator} {@code position + 1} times, returning the element at the {@code
     * position}th position or {@code defaultValue} otherwise.
     *
     * @param position     position of the element to return
     * @param defaultValue the default value to return if the iterator is empty or if {@code position}
     *                     is greater than the number of elements remaining in {@code iterator}
     * @return the element at the specified position in {@code iterator} or {@code defaultValue} if
     * {@code iterator} produces fewer than {@code position + 1} elements.
     * @throws IndexOutOfBoundsException if {@code position} is negative
     */
    public static <T> T get(
            Iterator<? extends T> iterator, int position, T defaultValue) {
        checkNonnegative(position);
        advance(iterator, position);
        return getNext(iterator, defaultValue);
    }

    static void checkNonnegative(int position) {
        if (position < 0) {
            throw new IndexOutOfBoundsException("position (" + position + ") must not be negative");
        }
    }

    /**
     * Returns the next element in {@code iterator} or {@code defaultValue} if the iterator is empty.
     * The {@link Iterables} analog to this method is {@link Iterables#getFirst}.
     *
     * @param defaultValue the default value to return if the iterator is empty
     * @return the next element of {@code iterator} or the default value
     */
    public static <T> T getNext(Iterator<? extends T> iterator, T defaultValue) {
        return iterator.hasNext() ? iterator.next() : defaultValue;
    }

    /**
     * Advances {@code iterator} to the end, returning the last element.
     *
     * @return the last element of {@code iterator}
     * @throws NoSuchElementException if the iterator is empty
     */
    public static <T> T getLast(Iterator<T> iterator) {
        while (true) {
            T current = iterator.next();
            if (!iterator.hasNext()) {
                return current;
            }
        }
    }

    /**
     * Advances {@code iterator} to the end, returning the last element or {@code defaultValue} if the
     * iterator is empty.
     *
     * @param defaultValue the default value to return if the iterator is empty
     * @return the last element of {@code iterator}
     */
    public static <T> T getLast(Iterator<? extends T> iterator, T defaultValue) {
        return iterator.hasNext() ? getLast(iterator) : defaultValue;
    }

    /**
     * Calls {@code next()} on {@code iterator}, either {@code numberToAdvance} times or until {@code
     * hasNext()} returns {@code false}, whichever comes first.
     *
     * @return the number of elements the iterator was advanced
     */
    public static int advance(Iterator<?> iterator, int numberToAdvance) {
        int i;
        for (i = 0; i < numberToAdvance && iterator.hasNext(); i++) {
            iterator.next();
        }
        return i;
    }
}
