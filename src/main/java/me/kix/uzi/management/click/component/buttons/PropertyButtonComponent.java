package me.kix.uzi.management.click.component.buttons;

import me.kix.uzi.api.property.Property;
import me.kix.sodapop.components.button.buttons.ButtonComponent;
import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.util.MouseButton;
import me.kix.sodapop.util.Rectangle;

/**
 * An implementation of {@link ButtonComponent} for properties.
 *
 * @author Kix
 * @since 6/28/2019
 */
public class PropertyButtonComponent extends ButtonComponent {

    /**
     * The property that this component handles.
     */
    private final Property<Boolean> property;

    public PropertyButtonComponent(String name, GuiManager guiManager, Rectangle renderPosition, Property<Boolean> property) {
        super(name, guiManager, renderPosition);
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
