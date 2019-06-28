package me.kix.uzi.api.property.properties;

import com.sun.istack.internal.NotNull;
import me.kix.uzi.api.property.Property;

/**
 * An instance of {@link me.kix.uzi.api.property.Property} for enums.
 *
 * <p>
 * This was originally written for Paradise in 2017 by me.
 * </p>
 *
 * @author Kix
 * @since 6/28/2019
 */
public class EnumProperty<T extends Enum<T>> extends Property<T> {

    /**
     * The actual "types" from the enum.
     */
    private final T[] constants;

    public EnumProperty(String label, T value) {
        super(label, value);
        this.constants = extractConstantsFromEnumValue(value);
    }

    /**
     * Gets the "types" from the enum.
     *
     * @param value The enum getting extracted from.
     * @return The array of constants.
     */
    private T[] extractConstantsFromEnumValue(T value) {
        return value.getDeclaringClass().getEnumConstants();
    }

    /**
     * Increments the current constant value.
     */
    public void increment() {
        T currentValue = getValue();

        for (T constant : constants) {
            if (constant != currentValue) {
                continue;
            }

            T newValue;

            int ordinal = constant.ordinal();
            if (ordinal == constants.length - 1) {
                newValue = constants[0];
            } else {
                newValue = constants[ordinal + 1];
            }

            setValue(newValue);
            return;
        }
    }

    /**
     * Decrements the current constant value.
     */
    public void decrement() {
        T currentValue = getValue();

        for (T constant : constants) {
            if (constant != currentValue) {
                continue;
            }

            T newValue;

            int ordinal = constant.ordinal();
            if (ordinal == 0) {
                newValue = constants[constants.length - 1];
            } else {
                newValue = constants[ordinal - 1];
            }

            setValue(newValue);
            return;
        }
    }

    /**
     * Sets the value based on a string parameter.
     *
     * @param string The new value in string form.
     */
    public void setValue(@NotNull String string) {
        for (T constant : constants) {
            if (constant.name().equalsIgnoreCase(string)) {
                setValue(constant);
            }
        }
    }

    /**
     * @return The string form of the value.
     */
    public String getFixedValue() {
        return getValue().toString();
    }

    public T[] getConstants() {
        return constants;
    }

}
