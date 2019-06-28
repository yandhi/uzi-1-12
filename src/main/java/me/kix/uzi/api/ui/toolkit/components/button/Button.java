package me.kix.uzi.api.ui.toolkit.components.button;

import me.kix.uzi.api.ui.toolkit.util.MouseButton;

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
