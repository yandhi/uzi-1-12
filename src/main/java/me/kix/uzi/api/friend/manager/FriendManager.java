package me.kix.uzi.api.friend.manager;

import com.google.gson.*;
import me.kix.uzi.api.friend.Friend;
import me.kix.uzi.api.manager.ListManager;

import java.io.*;
import java.util.Map;
import java.util.Set;

public class FriendManager extends ListManager<Friend> {

    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private File friendFile;

    public FriendManager(File directory) {
        friendFile = new File(directory.toString() + File.separator + "friends.json");
    }

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

    public void load() {
        try {
            final JsonObject object = new JsonParser().parse(new FileReader(friendFile)).getAsJsonObject();
            final Set<Map.Entry<String, JsonElement>> elements = object.entrySet();
            elements.forEach(entry -> getContents().add(new Friend(entry.getKey(), entry.getValue().getAsString())));
        } catch (FileNotFoundException ignored) {

        }
    }

    public String getReplacedText(String text) {
        for (Friend friend : getContents()) {
            if (text != null && text.contains(friend.getLabel()))
                return text.replaceAll(friend.getLabel(), "\2473" + friend.getAlias() + "\247f");
        }
        return text;
    }

    public void removeFriend(String ign) {
        for (Friend friend : getContents()) {
            if (friend.getLabel().equalsIgnoreCase(ign))
                getContents().remove(friend);
        }
    }

    public boolean isFriend(String ign) {
        for (Friend friend : getContents()) {
            if (friend.getLabel().equalsIgnoreCase(ign))
                return true;
        }
        return false;
    }

}
