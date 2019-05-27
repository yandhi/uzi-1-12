package me.kix.uzi.api.action.actions;

import me.kix.uzi.api.action.Action;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;

public class TogglePluginAction extends Action {

    private final ToggleablePlugin plugin;

    public TogglePluginAction(ToggleablePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void performAction() {
        plugin.toggle();
    }

    public ToggleablePlugin getPlugin() {
        return plugin;
    }
}
