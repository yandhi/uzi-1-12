package me.kix.uzi.api.util.math.parsing;

/**
 * @author N3xuz_DK
 */
public final class NumberPropParser {

    public static <T extends Number> T parse(final String input, final Class<T> numberType) throws NumberFormatException {
        return NumberCaster.cast(numberType, Double.parseDouble(input));
    }

}
