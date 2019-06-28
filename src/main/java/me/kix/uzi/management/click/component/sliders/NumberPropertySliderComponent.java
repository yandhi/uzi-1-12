package me.kix.uzi.management.click.component.sliders;

import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.ui.toolkit.components.slider.SliderComponent;
import me.kix.uzi.api.ui.toolkit.theme.Theme;
import me.kix.uzi.api.ui.toolkit.util.Rectangle;

/**
 * The implementation of {@link me.kix.uzi.api.ui.toolkit.components.slider.SliderComponent} for {@link me.kix.uzi.api.property.properties.NumberProperty}.
 *
 * @author Kix
 * @since 6/28/2019
 */
public class NumberPropertySliderComponent extends SliderComponent {

    /**
     * The property that this component handles.
     */
    private final NumberProperty property;

    public NumberPropertySliderComponent(String name, Theme theme, Rectangle renderPosition, NumberProperty property) {
        super(name, theme, renderPosition);
        this.property = property;
    }

    @Override
    protected void setValue(Number value) {
        property.setValue(value);
    }

    @Override
    protected Number value() {
        return (Number) property.getValue();
    }

    @Override
    protected Number minimum() {
        return property.getMinimum();
    }

    @Override
    protected Number maximum() {
        return property.getMaximum();
    }

    public NumberProperty getProperty() {
        return property;
    }
}
