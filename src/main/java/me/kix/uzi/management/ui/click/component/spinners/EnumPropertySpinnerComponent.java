package me.kix.uzi.management.ui.click.component.spinners;

import me.kix.uzi.api.property.properties.EnumProperty;
import me.kix.sodapop.components.spinner.SpinnerComponent;
import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.util.Rectangle;

/**
 * The implementation of {@link SpinnerComponent} for {@link me.kix.uzi.api.property.properties.EnumProperty}.
 *
 * @author Kix
 * @since 6/28/2019
 */
public class EnumPropertySpinnerComponent extends SpinnerComponent {

    /**
     * The property that this component handles.
     */
    private final EnumProperty property;

    public EnumPropertySpinnerComponent(String name, GuiManager guiManager, Rectangle renderPosition, EnumProperty property) {
        super(name, guiManager, renderPosition);
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
