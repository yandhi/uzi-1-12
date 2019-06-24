package me.kix.uzi.api.friend.manager;

import com.google.gson.*;
import me.kix.uzi.api.friend.Friend;
import me.kix.uzi.api.manager.ListManager;

import java.io.*;
import java.util.Map;
import java.util.Set;

/**
 * The manager for all allies in the client.
 *
 * <p>
 * This is an ideal example of an instance where {@link ListManager} is useful.
 * </p>
 *
 * @author Kix
 * @since May 2018 (Revised June 2019).
 */
public class FriendManager extends ListManager<Friend> {

    /**
     * An instance of Google's JSON library in pretty-printed form.
     */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * The file where friends are loaded / saved.
     */
    private File friendFile;

    public FriendManager(File directory) {
        friendFile = new File(directory.toString() + File.separator + "friends.json");
    }

    /**
     * Initializes the friend manager.
     */
    public void init() {
        try {
            if (!friendFile.exists()) {
                friendFile.createNewFile();
                save();
                return;
            }
            load();
        } catch (IOException ignored) {

        }
    }

    /**
     * Saves the current friend system.
     */
    public void save() {
        try {
            final PrintWriter writer = new PrintWriter(friendFile);
            final JsonObject object = new JsonObject();
            getContents().forEach(friend -> object.add(friend.getLabel(), new JsonPrimitive(friend.getAlias())));
            writer.print(GSON.toJson(object));
            writer.close();
        } catch (FileNotFoundException ignored) {

        }
    }

    /**
     * Loads the current friend system.
     */
    public void load() {
        try {
            final JsonObject object = new JsonParser().parse(new FileReader(friendFile)).getAsJsonObject();
            final Set<Map.Entry<String, JsonElement>> elements = object.entrySet();
            elements.forEach(entry -> getContents().add(new Friend(entry.getKey(), entry.getValue().getAsString())));
        } catch (FileNotFoundException ignored) {

        }
    }

    /**
     * Replaces the given text with the friends changed.
     *
     * @param text The text to be replaced.
     * @return The new instance of the text.
     */
    public String getReplacedText(String text) {
        for (Friend friend : getContents()) {
            if (text != null && text.contains(friend.getLabel()))
                return text.replaceAll(friend.getLabel(), "\2473" + friend.getAlias() + "\247f");
        }
        return text;
    }

    /**
     * Removes a friend based on the provided in-game name.
     *
     * @param ign The in-game name of the friend being removed.
     */
    public void removeFriend(String ign) {
        for (Friend friend : getContents()) {
            if (friend.getLabel().equalsIgnoreCase(ign))
                getContents().remove(friend);
        }
    }

    /**
     * Whether or not the player with the given in-game name is a friend.
     *
     * @param ign The in-game name of the player being checked.
     * @return Whether or not the player is a friend.
     */
    public boolean isFriend(String ign) {
        for (Friend friend : getContents()) {
            if (friend.getLabel().equalsIgnoreCase(ign))
                return true;
        }
        return false;
    }

}
