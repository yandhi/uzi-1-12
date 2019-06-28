package me.kix.uzi.api.ui.toolkit;

import me.kix.uzi.api.ui.toolkit.theme.Theme;
import me.kix.uzi.api.ui.toolkit.theme.renderer.ComponentRenderer;
import me.kix.uzi.api.ui.toolkit.util.MouseButton;
import me.kix.uzi.api.ui.toolkit.util.Rectangle;

/**
 * A class for a drawble component.
 *
 * @author Kix
 * @since 6/26/2019
 */
public interface Component {

    /**
     * Draws the component on the screen.
     *
     * @param renderer     The renderer for the component.
     * @param mouseX       The x position of the mouse.
     * @param mouseY       The y position of the mouse.
     * @param partialTicks The renderer ticks amount.
     */
    void drawComponent(ComponentRenderer renderer, int mouseX, int mouseY, float partialTicks);

    /**
     * Handles mouse press interaction for the component.
     *
     * @param mouseX      The x position of the mouse.
     * @param mouseY      The y position of the mouse.
     * @param mouseButton The button that is being pressed.
     */
    void mousePressed(int mouseX, int mouseY, MouseButton mouseButton);

    /**
     * Handles mouse release interaction for the component.
     *
     * @param mouseX      The x position of the mouse.
     * @param mouseY      The y position of the mouse.
     * @param mouseButton The button that is being released.
     */
    void mouseReleased(int mouseX, int mouseY, MouseButton mouseButton);

    /**
     * Updates the position of the component.
     *
     * @param x The new x position of the component.
     * @param y The new y position of the component.
     */
    void updatePosition(int x, int y);

    /**
     * Updates the dimensions of the component.
     *
     * @param width  The width of the component.
     * @param height The height of the component.
     */
    void updateDimensions(int width, int height);

    /**
     * Updates the component.
     */
    void updateComponent(int mouseX, int mouseY);

    /**
     * Initializes the component.
     */
    void initComponent();

    /**
     * @return The name of the component.
     */
    String getName();

    /**
     * @return The position for functions such as pressing the mouse.
     */
    Rectangle getFunctionalPosition();

    /**
     * @return The position for displaying to the user.
     */
    Rectangle getRenderPosition();

    /**
     * @return The themes that defines the look and feel of the component.
     */
    Theme getTheme();
}