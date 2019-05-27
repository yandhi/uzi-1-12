package me.kix.uzi.management.command.commands;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.command.Command;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.logging.Logger;

public class PluginsCommand extends Command {

    public PluginsCommand() {
        super("Plugins", new String[]{"pl", "plug", "mods", "modules", "pluginlist"}, "Shows all plugins in the client.");
    }

    @Override
    public void execute(String args) {
        StringBuilder builder = new StringBuilder("Plugins [\2477" + Uzi.INSTANCE.getPluginManager().getContents().stream().filter(plug -> plug instanceof ToggleablePlugin).toArray().length + "\247f]\2478: ");
        Uzi.INSTANCE.getPluginManager().getContents().stream().filter(p -> p instanceof ToggleablePlugin).forEach(p -> builder.append("\247").append(((ToggleablePlugin) p).isEnabled() ? "a" : "c").append(p.getLabel() + "\2477, "));
        Logger.printMessage(builder.toString().substring(0, builder.length() - 2));
    }
}
