package me.kix.uzi.management.ui.click.component.sliders;

import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.sodapop.components.slider.SliderComponent;
import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.util.Rectangle;

/**
 * The implementation of {@link SliderComponent} for {@link me.kix.uzi.api.property.properties.NumberProperty}.
 *
 * @author Kix
 * @since 6/28/2019
 */
public class NumberPropertySliderComponent extends SliderComponent {

    /**
     * The property that this component handles.
     */
    private final NumberProperty property;

    public NumberPropertySliderComponent(String name, GuiManager guiManager, Rectangle renderPosition, NumberProperty property) {
        super(name, guiManager, renderPosition);
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
