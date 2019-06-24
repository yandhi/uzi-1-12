package me.kix.uzi.api.keybind;

import me.kix.uzi.api.keybind.task.KeybindTaskStrategy;
import me.kix.uzi.api.util.interfaces.Labeled;

/**
 * This acts as a macro for in-client actions.
 *
 * <p>
 * Basically, we hook into Minecraft's keybinding system in order to allow ourselves to execute tasks based on a single
 * keypress.
 * </p>
 *
 * @author Kix
 * @since May 2018 (Revised June 2019).
 */
public class Keybind implements Labeled {

    /**
     * The name of the keybind.
     */
    private final String label;

    /**
     * The key that needs to be pressed in order for the taskStrategy to execute.
     */
    private String key;

    /**
     * The strategy for a keybind press.
     *
     * <p>
     * This is the task that executes based on the press.
     * </p>
     */
    private final KeybindTaskStrategy taskStrategy;

    public Keybind(String label, String key, KeybindTaskStrategy taskStrategy) {
        this.label = label;
        this.key = key;
        this.taskStrategy = taskStrategy;
    }

    /**
     * This function is executed on a single key press.
     *
     * <p>
     * This executes the {@link KeybindTaskStrategy} provided for the keybind.
     * </p>
     */
    public void onPress() {
        taskStrategy.executeTask();
    }

    @Override
    public String getLabel() {
        return label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public KeybindTaskStrategy getTaskStrategy() {
        return taskStrategy;
    }
}
