package me.kix.uzi.management.ui.tab.item.impl.buttons;

import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.theme.renderer.ComponentRenderer;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.management.ui.tab.item.impl.ButtonTabComponent;

import java.awt.*;

/**
 * An implementation of {@link ButtonTabComponent} for Boolean proprties.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public class PropertyButtonTabComponent extends ButtonTabComponent {

    /**
     * The property that this button handles.
     */
    private final Property<Boolean> property;

    public PropertyButtonTabComponent(GuiManager guiManager, Rectangle renderPosition, Property<Boolean> property) {
        super(property.getLabel(), guiManager, renderPosition);
        this.property = property;
    }

    @Override
    public void drawComponent(ComponentRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        this.setState(property.getValue());
        super.drawComponent(renderer, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void executeAction() {
        property.setValue(!property.getValue());
    }
}
