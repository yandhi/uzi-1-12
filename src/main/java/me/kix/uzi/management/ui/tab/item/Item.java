package me.kix.uzi.management.ui.tab.item;

import me.kix.uzi.api.util.interfaces.Labeled;

/**
 * An item in the tabgui.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public interface Item extends Labeled {

    /**
     * Draws the item.
     *
     * @param x          The x position of the item.
     * @param y          The y position of the item.
     * @param width      The width of the item.
     * @param height     The height of the item.
     * @param foreground The accent color of the item.
     * @param background The background color of the item.
     */
    void draw(int x, int y, int width, int height, int foreground, int background);

    /**
     * Handles key presses for the item.
     *
     * @param keyCode The key being pressed.
     */
    void handleKeys(int keyCode);

    /**
     * @return Whether or not the item is hovered.
     */
    boolean isHovered();

    /**
     * Sets the item to hovered.
     *
     * @param hovered The new hovered state.
     */
    void setHovered(boolean hovered);
}
