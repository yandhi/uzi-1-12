package me.kix.uzi.api.command.parsing.parsers;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.command.parsing.Parser;
import me.kix.uzi.api.plugin.Plugin;

import java.util.Optional;

public class PluginParser extends Parser<Plugin> {

    @Override
    public Plugin parse(String input) {
        Optional<Plugin> found = Uzi.INSTANCE.getPluginManager().getPlugin(input);
        return found.orElse(null);
    }

    @Override
    public boolean canHandleType(Class type) {
        return Plugin.class.isAssignableFrom(type);
    }
}
