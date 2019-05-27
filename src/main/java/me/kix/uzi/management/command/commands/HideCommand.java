package me.kix.uzi.management.command.commands;

import me.kix.uzi.api.command.Command;
import me.kix.uzi.api.command.parsing.parsers.PluginParser;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.logging.Logger;

public class HideCommand extends Command {

    private final PluginParser parser = new PluginParser();

    public HideCommand() {
        super("Hide", new String[]{"Hidden", "Visable", "Visible", "GetRidOf"}, "Allows the user to hide a plugin.");
    }

    @Override
    public void execute(String args) {
        Plugin parsed = parser.parse(args.split(" ")[1]);
        if (parsed != null) {
            if (parsed instanceof ToggleablePlugin)
                ((ToggleablePlugin) parsed).setHidden(!((ToggleablePlugin) parsed).isHidden());
            else Logger.printMessage("Plugin is not toggleable.");
        } else Logger.printMessage("Plugin not found!");
    }
}
