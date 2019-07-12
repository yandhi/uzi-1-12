package me.kix.sodapop.util;

/**
 * An enum class for handling the mouse buttons.
 *
 * @author Kix
 * @since 6/26/2019
 */
public enum MouseButton {

    NONE(-1),
    LEFT(0),
    RIGHT(1),
    MIDDLE(2);

    /**
     * The button that the type handles.
     */
    private int button;

    MouseButton(int button) {
        this.button = button;
    }

    /**
     * Gets the enum type of the button based on the button integer form.
     *
     * @param button The button id being searched for.
     * @return The typed instance of the button.
     */
    public static MouseButton getButton(int button) {
        for (MouseButton mouseButton : MouseButton.values()) {
            if (mouseButton.getButton() == button) {
                return mouseButton;
            }
        }
        return MouseButton.NONE;
    }

    public int getButton() {
        return button;
    }
}
