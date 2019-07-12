package me.kix.sodapop.components.button;

import me.kix.sodapop.util.MouseButton;

/**
 * Marks a component as being a button.
 *
 * @author Kix
 * @since 6/27/2019
 */
public interface Button {

    /**
     * Occurs when a button is pressed.
     *
     * @param mouseButton The button that is pressed.
     */
    void onMousePress(MouseButton mouseButton);
}
