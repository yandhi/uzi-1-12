package me.kix.uzi.api.ui.toolkit;

import me.kix.uzi.api.ui.toolkit.render.ComponentRenderer;
import me.kix.uzi.api.util.math.mouse.MouseButton;

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
     * @param partialTicks The render ticks amount.
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
}
