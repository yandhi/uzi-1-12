package me.kix.uzi.management.command.commands;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.kix.uzi.api.command.Command;
import me.kix.uzi.api.connection.ConnectionStrategy;
import me.kix.uzi.api.connection.impl.GetRequestConnectionStrategy;
import me.kix.uzi.api.util.logging.Logger;

/**
 * Automatically tells us the name history of a player.
 * 
 * <p>
 * This is also an extremely rare occurance of jackson using the java.net
 * package.
 * </p>
 * 
 * @author Jackson
 * @since April 2019
 */
public class NameHistoryCommand extends Command {

    /**
     * The strategy for connecting to the mojang api.
     */
    private final ConnectionStrategy connectionStrategy = new GetRequestConnectionStrategy();

    public NameHistoryCommand() {
        super("NameHistory", new String[] { "nh", "names", "history" }, "Tells us the name history of a player.");
    }

    @Override
    public void execute(String args) {
        String[] splitArgs = args.split(" ");
        String ign = splitArgs[1];
        String uuid = getUUIDFromIGN(ign);
        Set<String> nameHistory = getNameHistoryFromUUID(uuid);
        StringBuilder builder = new StringBuilder("Name History [\2477" + nameHistory.size() + "\247f]: ");
        for (String name : nameHistory) {
            builder.append(name).append("\2477, \247f");
        }
        Logger.printMessage(builder.toString().substring(0, builder.length() - 2));
    }

    /**
     * Utilizes the mojang names api in order to gain name history through the uuid
     * found in {@link #getUUIDFromIGN(String)}
     * 
     * @param uuid The UUID of the player being searched for in name history.
     */
    private Set<String> getNameHistoryFromUUID(String uuid) {
        Set<String> nameHistory = new HashSet<>();
        JsonElement mainElement = new JsonParser()
                .parse(connectionStrategy.connect("https://api.mojang.com/user/profiles/" + uuid + "/names"));
        for (JsonElement element : mainElement.getAsJsonArray()) {
            if (element.isJsonObject()) {
                nameHistory.add(element.getAsJsonObject().get("name").getAsString());
            }
        }
        return nameHistory;
    }

    /**
     * Utilizes the mojang api in order to tell us the UUID based on the current
     * ign.
     * 
     * @param ign The in-game name for the player being searched for.
     * @return If the URL connection is sucessful, the uuid of the ign provided;
     */
    private String getUUIDFromIGN(String ign) {
        long unixTime = System.currentTimeMillis() / 1000L;
        JsonElement mainElement = new JsonParser().parse(connectionStrategy.connect("https://api.mojang.com/users/profiles/minecraft/" + ign + "?at=" + unixTime));
        return mainElement.getAsJsonObject().get("id").getAsString();
    }
}