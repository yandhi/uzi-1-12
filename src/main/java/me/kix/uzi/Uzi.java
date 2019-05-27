package me.kix.uzi;

import me.kix.uzi.api.event.EventManager;
import me.kix.uzi.api.friend.manager.FriendManager;
import me.kix.uzi.api.macro.manage.MacroManager;
import me.kix.uzi.api.util.interfaces.Client;
import me.kix.uzi.management.command.manage.CommandManager;
import me.kix.uzi.management.plugin.manage.PluginManager;
import me.kix.uzi.management.ui.alt.manage.AltManager;
import java.io.File;

/**
 * This is the 1.12.2 version of Uzi.
 *
 * <p>
 * Lord knows the exact day this version of Uzi was created.
 * I've came back to it roughly 4 times now.
 * It began some time during March of 2018.
 * </p>
 *
 * @author k1x
 */
public enum Uzi implements Client {
    INSTANCE;

    private final EventManager eventManager = new EventManager();
    private final CommandManager commandManager = new CommandManager();
    private final MacroManager macroManager = new MacroManager();
    private AltManager altManager;
    private FriendManager friendManager;
    private PluginManager pluginManager;
    private File directory;

    @Override
    public void init() {
        directory = new File(System.getProperty("user.home"), "Uzi");
        friendManager = new FriendManager(directory);
        pluginManager = new PluginManager(new File(directory, "Plugins"));
        altManager = new AltManager(directory);
        altManager.init();
        friendManager.init();
        pluginManager.init();
        commandManager.init();
        Runtime.getRuntime().addShutdownHook(new Thread("Uzi shutdown thread") {
            @Override
            public void run() {
                shutdown();
            }
        });
    }

    @Override
    public void shutdown() {
        if (!this.directory.exists()) {
            this.directory.mkdirs();
        }
        friendManager.save();
        pluginManager.save();
        altManager.save();
    }

    @Override
    public String getLabel() {
        return "Uzi";
    }

    @Override
    public String getVersion() {
        return "1.7.5";
    }

    @Override
    public String[] getAuthors() {
        return new String[]{"Kix"};
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public MacroManager getMacroManager() {
        return macroManager;
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }

    public AltManager getAltManager() {
        return altManager;
    }
}
