package me.kix.uzi.api.property.properties;

import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.util.math.clamping.NumberClamper;
import me.kix.uzi.api.util.math.parsing.NumberCaster;
import me.kix.uzi.api.util.math.parsing.NumberPropParser;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberProperty<T extends Number> extends Property<T> {

    private final T minimum;
    private final T maximum;
    private final T increment;

    public NumberProperty(String label, T value, T minimum, T maximum, T increment) {
        super(label, value);
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
    }

    public T getMinimum() {
        return minimum;
    }

    public T getMaximum() {
        return maximum;
    }

    public T getIncrement() {
        return increment;
    }

    @Override
    public void setValue(T value) {
        this.value = NumberClamper.clamp(NumberCaster.cast((Class<T>) ((Number) (getValue())).getClass(), roundToPlace(value.doubleValue(), 1)), minimum, maximum);
    }

    public void setValue(String value) {
        try {
            this.setValue(NumberPropParser.parse(value, (Class<T>) ((Number) getValue()).getClass()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.UP);
        return bd.doubleValue();
    }
}
