package me.kix.uzi.management.command.commands;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.action.actions.TogglePluginAction;
import me.kix.uzi.api.command.Command;
import me.kix.uzi.api.macro.Macro;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.logging.Logger;

import java.util.Optional;

public class MacroCommand extends Command {

    public MacroCommand() {
        super("Macro", new String[]{"bind", "keybind", "mcro", "kbind", "bnd"}, "Allows the user to bind a action to a certain key via macros.");
    }

    @Override
    public void execute(String args) {
        String[] split = args.split(" ");
        if (split.length > 2) {
            Optional<Plugin> givenPlugin = Uzi.INSTANCE.getPluginManager().getPlugin(split[1]);
            if (givenPlugin.isPresent()) {
                if (givenPlugin.get() instanceof ToggleablePlugin) {
                    ToggleablePlugin p = (ToggleablePlugin) givenPlugin.get();
                    Optional<Macro> givenMacro = Uzi.INSTANCE.getMacroManager().getMacro(p.getLabel());
                    if (givenMacro.isPresent()) {
                        givenMacro.get().setFacet(split[2].toUpperCase());
                        Logger.printMessage("Set facet to " + split[2].toUpperCase() + " for " + p.getLabel());
                    } else {
                        Uzi.INSTANCE.getMacroManager().getContents().add(new Macro(p.getLabel(), split[2].toUpperCase(), new TogglePluginAction(p)));
                        Logger.printMessage("Set facet to " + split[2].toUpperCase() + " for " + p.getLabel());
                    }
                } else {
                    Logger.printMessage("Given plugin was not found to be toggleable.");
                }
            } else {
                Logger.printMessage("Plugin was unable to be found.");
            }
        }
    }
}
