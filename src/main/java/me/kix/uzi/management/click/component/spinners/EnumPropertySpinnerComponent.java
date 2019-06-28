package me.kix.uzi.management.click.component.spinners;

import me.kix.uzi.api.property.properties.EnumProperty;
import me.kix.uzi.api.ui.toolkit.components.spinner.SpinnerComponent;
import me.kix.uzi.api.ui.toolkit.theme.Theme;
import me.kix.uzi.api.ui.toolkit.util.Rectangle;

/**
 * The implementation of {@link me.kix.uzi.api.ui.toolkit.components.spinner.SpinnerComponent} for {@link me.kix.uzi.api.property.properties.EnumProperty}.
 *
 * @author Kix
 * @since 6/28/2019
 */
public class EnumPropertySpinnerComponent extends SpinnerComponent {

    /**
     * The property that this component handles.
     */
    private final EnumProperty property;

    public EnumPropertySpinnerComponent(String name, Theme theme, Rectangle renderPosition, EnumProperty property) {
        super(name, theme, renderPosition);
        this.property = property;
    }

    @Override
    protected void increment() {
        property.increment();
    }

    @Override
    protected void decrement() {
        property.decrement();
    }

    public EnumProperty getProperty() {
        return property;
    }
}
