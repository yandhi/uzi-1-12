package me.kix.uzi.api.game.accessors.client.key;

/**
 * The outline for the game's keybinding system.
 *
 * <p>
 * This allows us to manually set a key to pressed without actually touching it with our keyboard.
 * </p>
 *
 * @author Kix
 * @since June 2018.
 */
public interface GameKeybinding {

    /**
     * Sets the keybind to a different pressed key state.
     *
     * @param pressed The new pressed key state of the keybind.
     */
    void setPressed(boolean pressed);
}