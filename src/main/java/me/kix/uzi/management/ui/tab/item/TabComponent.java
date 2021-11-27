package me.kix.uzi.management.ui.tab.item;

import me.kix.sodapop.AbstractComponent;
import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.util.Rectangle;

/**
 * The abstract form of {@link Item}.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public class TabComponent extends AbstractComponent {

    /**
     * Whether or not the item is hovered.
     */
    private boolean hovered;

    public TabComponent(String name, GuiManager guiManager, Rectangle renderPosition) {
        super(name, guiManager, renderPosition);
    }

    /**
     * Handles key presses for the item.
     *
     * @param keyCode The key being pressed.
     */
    public void handleKeys(int keyCode) {}

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }
}
