package me.kix.uzi.management.command.commands;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.command.argument.factory.registration.RegisterArgument;
import me.kix.uzi.api.command.commands.ArgumentativeCommand;
import me.kix.uzi.api.keybind.task.tasks.SendMessageKeybindTaskStrategy;
import me.kix.uzi.api.keybind.task.tasks.TogglePluginKeybindTaskStrategy;
import me.kix.uzi.api.keybind.Keybind;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.logging.Logger;

import java.util.Optional;

/**
 * A command for keybinds.
 *
 * @author Kix
 * @since 7/19/2019.
 */
public class KeybindCommand extends ArgumentativeCommand {

    public KeybindCommand() {
        super("Keybind", new String[]{"bind", "keybind", "mcro", "kbind", "bnd"}, "Allows the user to bind a action to a certain key via macros.");
    }

    @RegisterArgument({"plugin", "module", "mod"})
    public void module(String moduleName, String key) {
        Optional<Plugin> givenPlugin = Uzi.INSTANCE.getPluginManager().getPlugin(moduleName);
        if (givenPlugin.isPresent()) {
            if (givenPlugin.get() instanceof ToggleablePlugin) {
                ToggleablePlugin p = (ToggleablePlugin) givenPlugin.get();
                Optional<Keybind> givenMacro = Uzi.INSTANCE.getKeybindManager().getKeybind(p.getLabel());
                if (givenMacro.isPresent()) {
                    givenMacro.get().setKey(key.toUpperCase());
                    Logger.printMessage("Set facet to " + key.toUpperCase() + " for " + p.getLabel());
                } else {
                    Uzi.INSTANCE.getKeybindManager().getContents().add(new Keybind(p.getLabel(), key.toUpperCase(), new TogglePluginKeybindTaskStrategy(p)));
                    Logger.printMessage("Set facet to " + key.toUpperCase() + " for " + p.getLabel());
                }
            } else {
                Logger.printMessage("Given plugin was not found to be toggleable.");
            }
        } else {
            Logger.printMessage("Plugin was unable to be found.");
        }
    }

    @RegisterArgument({"message", "msg"})
    public void message(String name, String message, String key) {
        if (!Uzi.INSTANCE.getKeybindManager().getKeybind(name).isPresent()) {
            Keybind keybind = new Keybind(name, key.toUpperCase(), new SendMessageKeybindTaskStrategy(message));
            Uzi.INSTANCE.getKeybindManager().getContents().add(keybind);
            Logger.printMessage("Bound \"" + message + "\" to " + key.toUpperCase() + " nicknamed as " + name + ".");
        } else {
            Logger.printMessage("That keybind already exists!");
        }
    }

    @RegisterArgument({"remove"})
    public void remove(String name) {
        Optional<Keybind> foundKeybind = Uzi.INSTANCE.getKeybindManager().getKeybind(name);
        foundKeybind.ifPresent(keybind -> Uzi.INSTANCE.getKeybindManager().getContents().remove(keybind));
    }
}
