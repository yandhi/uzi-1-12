package me.kix.uzi.api.friend.manager;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import me.kix.uzi.api.friend.Friend;
import me.kix.uzi.api.manager.ListManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

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
    private Path friendFile;

    public FriendManager(Path directory) {
        friendFile = Paths.get(directory.toString() + File.separator + "friends.json").toAbsolutePath();
    }

    /**
     * Initializes the friend manager.
     */
    public void init() {
        try {
            if (!Files.exists(friendFile)) {
                Files.createFile(friendFile);
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
            final JsonObject object = new JsonObject();
            getContents().forEach(friend -> object.add(friend.getLabel(), new JsonPrimitive(friend.getAlias())));
            Files.write(friendFile, GSON.toJson(object).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the current friend system.
     */
    public void load() {
        try {
            final JsonObject object = JsonParser.parseReader(new JsonReader(Files.newBufferedReader(friendFile))).getAsJsonObject();
            final Set<Map.Entry<String, JsonElement>> elements = object.entrySet();
            elements.forEach(entry -> getContents().add(new Friend(entry.getKey(), entry.getValue().getAsString())));
        } catch (IOException e) {
            e.printStackTrace();
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
            if (text != null) {
                text = text.replaceAll("(?i)" + Matcher.quoteReplacement(friend.getLabel()), Matcher.quoteReplacement("\247b" + friend.getAlias() + "\247r"));
            }
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
