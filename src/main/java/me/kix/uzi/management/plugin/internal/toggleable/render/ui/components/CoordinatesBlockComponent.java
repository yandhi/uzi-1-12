package me.kix.uzi.management.plugin.internal.toggleable.render.ui.components;

import me.kix.sodapop.AbstractComponent;
import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.util.Rectangle;

/**
 * A block component for the coordinates.
 *
 * @author jackson
 * @since 11/25/2021
 */
public class CoordinatesBlockComponent extends AbstractComponent {

    public CoordinatesBlockComponent(GuiManager guiManager, Rectangle renderPosition) {
        super("Coordinates", guiManager, renderPosition);
    }
}
