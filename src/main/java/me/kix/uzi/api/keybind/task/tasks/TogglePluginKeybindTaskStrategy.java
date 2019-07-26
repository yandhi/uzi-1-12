package me.kix.uzi.api.keybind.task.tasks;

import me.kix.uzi.api.keybind.task.KeybindTaskStrategy;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;

/**
 * The toggleable plugin strategy for {@link KeybindTaskStrategy}.
 *
 * <p>
 * On a keypress, the plugin is toggled.
 * </p>
 *
 * @author Kix
 * @since May 2018 (Revised June 2019).
 */
public class TogglePluginKeybindTaskStrategy implements KeybindTaskStrategy {

    /**
     * The plugin that the strategy handles.
     *
     * <p>
     * This is the plugin being toggled on the keypress.
     * </p>
     */
    private final ToggleablePlugin plugin;

    public TogglePluginKeybindTaskStrategy(ToggleablePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void executeTask() {
        plugin.toggle();
    }

    public ToggleablePlugin getPlugin() {
        return plugin;
    }

    @Override
    public String getStrategy() {
        return "Toggle";
    }
}
