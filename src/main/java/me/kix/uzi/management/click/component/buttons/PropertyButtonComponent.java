package me.kix.uzi.management.click.component.buttons;

import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.ui.toolkit.components.button.buttons.ButtonComponent;
import me.kix.uzi.api.ui.toolkit.theme.Theme;
import me.kix.uzi.api.ui.toolkit.util.MouseButton;
import me.kix.uzi.api.ui.toolkit.util.Rectangle;

/**
 * An implementation of {@link me.kix.uzi.api.ui.toolkit.components.button.buttons.ButtonComponent} for properties.
 *
 * @author Kix
 * @since 6/28/2019
 */
public class PropertyButtonComponent extends ButtonComponent {

    /**
     * The property that this component handles.
     */
    private final Property<Boolean> property;

    public PropertyButtonComponent(String name, Theme theme, Rectangle renderPosition, Property<Boolean> property) {
        super(name, theme, renderPosition);
        this.property = property;
    }

    @Override
    public void onMousePress(MouseButton mouseButton) {
        if (mouseButton == MouseButton.LEFT) {
            property.setValue(!property.getValue());
        }
    }

    public Property<Boolean> getProperty() {
        return property;
    }
}
