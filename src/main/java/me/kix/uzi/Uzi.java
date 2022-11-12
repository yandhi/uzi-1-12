package me.kix.uzi;

import me.kix.uzi.api.event.EventManager;
import me.kix.uzi.api.friend.manager.FriendManager;
import me.kix.uzi.api.keybind.manage.KeybindManager;
import me.kix.uzi.api.util.interfaces.Client;
import me.kix.uzi.management.command.manage.CommandManager;
import me.kix.uzi.management.plugin.manage.PluginManager;
import me.kix.uzi.management.ui.alt.manage.AltManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This is the 1.12.2 version of Uzi.
 *
 * <p>
 * Lord knows the exact day this version of Uzi was created.
 * I've came back to it roughly 4 times now.
 * It began some time during March of 2018.
 * </p>
 *
 * <p>
 * This utility mod is massive, like, extremely.
 * I'm working on trying to make it easier to use.
 * </p>
 *
 * @author k1x
 * @since April 2018 (Revised June 2019).
 */
public enum Uzi implements Client {

    /**
     * The singleton instance of the client.
     */
    INSTANCE;

    /**
     * The event system for the client.
     */
    private final EventManager eventManager = new EventManager();

    /**
     * The command system for the client.
     */
    private final CommandManager commandManager = new CommandManager();

    /**
     * The keybinding system for the client.
     */
    private KeybindManager keybindManager;

    /**
     * The alt system for the client.
     */
    private AltManager altManager;

    /**
     * The friend system for the client.
     */
    private FriendManager friendManager;

    /**
     * The plugin system for the client.
     */
    private PluginManager pluginManager;

    /**
     * The directory of the client.
     */
    private Path directory;

    @Override
    public void init() {
        try {
            directory = Paths.get(System.getProperty("user.home"), "Uzi");
            if(!Files.exists(directory)) {
                Files.createDirectory(directory);
            }
            friendManager = new FriendManager(directory);
            keybindManager = new KeybindManager(directory);
            pluginManager = new PluginManager(Paths.get(directory.toString(), "Plugins").toAbsolutePath());
            altManager = new AltManager(directory);
            altManager.init();
            friendManager.init();
            pluginManager.init();
            keybindManager.init();
            commandManager.init();


        } catch (IOException e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().addShutdownHook(new Thread("Uzi shutdown thread") {
            @Override
            public void run() {
                shutdown();
            }
        });
    }

    @Override
    public void shutdown() {
        friendManager.save();
        pluginManager.save();
        altManager.save();
        keybindManager.save();
    }

    @Override
    public String getLabel() {
        return "Uzi";
    }

    @Override
    public String getVersion() {
        return "2.0.3";
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

    public KeybindManager getKeybindManager() {
        return keybindManager;
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }

    public AltManager getAltManager() {
        return altManager;
    }

    public Path getDirectory() {
        return directory;
    }
}