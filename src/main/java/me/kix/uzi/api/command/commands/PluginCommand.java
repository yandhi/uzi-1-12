package me.kix.uzi.api.command.commands;

import me.kix.uzi.api.command.Command;
import me.kix.uzi.api.command.parsing.parsers.StringParser;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.logging.Logger;

/**
 * An implementation of {@link Command} for plugins.
 *
 * @author Kix
 * @since 4/19/2019
 */
public class PluginCommand extends Command {

    /**
     * The plugin this command is for.
     */
    private final Plugin plugin;

    public PluginCommand(Plugin plugin) {
        super(plugin.getLabel(), new String[]{plugin.getLabel().toLowerCase() + "plugin"}, "Allows the user to change values for " + plugin.getLabel() + ".");
        this.plugin = plugin;
    }

    @Override
    public void execute(String args) {
        System.out.println(args);
        for (Property property : plugin.getProperties()) {
            if (args.split(" ")[1].equalsIgnoreCase(property.getLabel())) {
                if (property.getValue() instanceof Boolean) {
                    property.setValue(!(Boolean) property.getValue());
                    Logger.printMessage(property.getLabel() + " has been toggled " + ((Boolean) property.getValue() ? "on" : "off"));
                } else if (property.getValue() instanceof String) {
                    final StringParser parser = new StringParser();
                    property.setValue(parser.parse(args.split(" ")[2]));
                    Logger.printMessage(property.getLabel() + " has been set as \"" + property.getValue() + "\".");
                } else if (property instanceof NumberProperty) {
                    if (args.split(" ").length > 2) {
                        NumberProperty numProp = (NumberProperty) property;
                        numProp.setValue(args.split(" ")[2]);
                        Logger.printMessage(property.getLabel() + " has been set to " + property.getValue());
                    }
                }
            }
        }
    }
}