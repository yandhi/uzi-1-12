package me.kix.uzi.api.keybind.task;

/**
 * Defines a task for a keybind.
 *
 * This utilizes the strategy design pattern.
 *
 * @author Kix
 * @since May 2018 (Revised June 2019).
 */
public interface KeybindTaskStrategy {

    /**
     * Performs the action designed for the keybind.
     */
    void executeTask();
}
