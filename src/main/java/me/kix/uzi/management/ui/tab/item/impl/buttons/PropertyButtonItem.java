package me.kix.uzi.management.ui.tab.item.impl.buttons;

import me.kix.uzi.api.property.Property;
import me.kix.uzi.management.ui.tab.item.impl.ButtonItem;

import java.awt.*;

/**
 * An implementation of {@link me.kix.uzi.management.ui.tab.item.impl.ButtonItem} for Boolean proprties.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public class PropertyButtonItem extends ButtonItem {

    /**
     * The property that this button handles.
     */
    private final Property<Boolean> property;

    public PropertyButtonItem(Property<Boolean> property) {
        super(property.getLabel());
        this.property = property;
    }

    @Override
    public void draw(int x, int y, int width, int height, int foreground, int background) {
        this.setState(property.getValue());
        super.draw(x, y, width, height, new Color(foreground).darker().getRGB(), background);
    }

    @Override
    protected void executeAction() {
        property.setValue(!property.getValue());
    }
}
