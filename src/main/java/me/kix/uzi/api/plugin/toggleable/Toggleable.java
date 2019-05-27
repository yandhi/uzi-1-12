package me.kix.uzi.api.plugin.toggleable;

/**
 * @author Kix
 * @since 5/6/2018
 */
public interface Toggleable {
    /**
     * Inverses the current state of the plugin.
     */
    void toggle();

    /**
     * @return Whether or not the plugin is enabled.
     */
    boolean isEnabled();

    /**
     * Changes the state of the plugin.
     *
     * @param enabled The new state of the plugin.
     */
    void setEnabled(boolean enabled);

    /**
     * Basic function which is hooked in the toggle method.
     * Mainly used for initializing certain objects on the enable of a plugin.
     */
    void onEnable();

    /**
     * Basic function which is hooked in the toggle method.
     * Mainly used for initializing certain objects on the disable of a plugin.
     */
    void onDisable();
}
