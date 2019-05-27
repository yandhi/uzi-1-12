package me.kix.uzi.management.command.commands;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.command.Command;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.logging.Logger;

import java.util.Optional;

public class ToggleCommand extends Command {

    public ToggleCommand() {
        super("Toggle", new String[]{"t", "tog"}, "Allows the user to toggle plugins.");
    }

    @Override
    public void execute(String args) {
        String[] split = args.split(" ");
        if (split.length >= 2) {
            Optional<Plugin> foundPlugin = Uzi.INSTANCE.getPluginManager().getPlugin(split[1]);
            if (foundPlugin.isPresent()) {
                Plugin plugin = foundPlugin.get();
                if (plugin instanceof ToggleablePlugin) {
                    ToggleablePlugin toggleablePlugin = (ToggleablePlugin) plugin;
                    toggleablePlugin.toggle();
                    Logger.printMessage(toggleablePlugin.getLabel() + " has been toggled " + (toggleablePlugin.isEnabled() ? "on" : "off") + ".");
                } else {
                    Logger.printMessage("Plugin is not toggleable.");
                }
            } else {
                Logger.printMessage("Plugin not found.");
            }
        }
    }
}
