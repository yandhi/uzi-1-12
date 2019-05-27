package me.kix.uzi.api.util.math.clamping;

public final class NumberClamper {

    private NumberClamper() {
    }

    /**
     * Attempts to clamp Numbers as a whole.
     *
     * @param value to be clamped.
     * @param min   number that the value can be.
     * @param max   number that the value can be
     * @param <T>   the generic supertype extending Number.
     * @return      the ultimately clamped number.
     */
    public static <T extends Number> T clamp(final T value, final T min, final T max) {
        return (((Comparable) value).compareTo(min) < 0) ? min : ((((Comparable) value).compareTo(max) > 0) ? max : value);
    }

}
