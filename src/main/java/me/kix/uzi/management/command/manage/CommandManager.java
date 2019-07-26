package me.kix.uzi.management.command.manage;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.command.Command;
import me.kix.uzi.api.command.commands.*;
import me.kix.uzi.api.manager.ListManager;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.management.command.commands.*;
import me.kix.uzi.management.plugin.internal.toggleable.render.Search;

public class CommandManager extends ListManager<Command> {

    public void init() {
        getContents().add(new KeybindCommand());
        getContents().add(new ToggleCommand());
        getContents().add(new PluginsCommand());
        getContents().add(new FriendCommand());
        getContents().add(new GodItemCommand());
        getContents().add(new HideCommand());
        getContents().add(new ServerCommand());
        getContents().add(new HelpCommand());
        getContents().add(new RotationsCommand());
        getContents().add(new WaypointsCommand());
        getContents().add(new NameHistoryCommand());
        getContents().add(new WhomstveWolfCommand());
        getContents().add(new ClipCommand());
        getContents().add(new SmartChatCommand());
        getContents().add(new SearchCommand());

        addPluginCommands();
        getContents().stream().filter(command -> command instanceof ArgumentativeCommand)
                .forEach(com -> ((ArgumentativeCommand) com).init());
    }

    private void addPluginCommands() {
        for (Plugin plugin : Uzi.INSTANCE.getPluginManager().getContents()) {
            if (!plugin.getProperties().isEmpty() && !(plugin instanceof Search)) {
                getContents().add(new PluginCommand(plugin));
            }
        }
    }

}
