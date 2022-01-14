package me.kix.uzi.management.ui.alt.manage;

import com.google.gson.*;
import me.kix.uzi.api.manager.ListManager;
import me.kix.uzi.management.ui.alt.Alt;

import java.io.*;
import java.util.Map;
import java.util.Set;

public class AltManager extends ListManager<Alt> {

    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private File altsFile;

    public AltManager(File directory) {
        altsFile = new File(directory.toString() + File.separator + "alts.json");
    }

    public void init() {
        try {
            if (!altsFile.exists()) {
                altsFile.createNewFile();
                save();
                return;
            }
            load();
        } catch (IOException ignored) {

        }
    }

    public void save() {
        try {
            final PrintWriter writer = new PrintWriter(altsFile);
            final JsonObject object = new JsonObject();
            getContents().forEach(alt -> {
                JsonObject altObject = new JsonObject();
                altObject.add("username", new JsonPrimitive(alt.getUsername() != null ? alt.getUsername() : alt.getEmail()));
                altObject.add("password", new JsonPrimitive(alt.getPassword()));

                object.add(alt.getEmail(), altObject);
            });
            writer.print(GSON.toJson(object));
            writer.close();
        } catch (FileNotFoundException ignored) {

        }
    }

    public void load() {
        try {
            final JsonObject object = new JsonParser().parse(new FileReader(altsFile)).getAsJsonObject();
            final Set<Map.Entry<String, JsonElement>> elements = object.entrySet();
            elements.forEach(entry -> {
                JsonObject alt = entry.getValue().getAsJsonObject();
                getContents().add(new Alt(alt.get("username").getAsString(), entry.getKey(), alt.get("password").getAsString()));
            });
        } catch (FileNotFoundException ignored) {
        }
    }

    public void remove(String username) {
        for (Alt alt : getContents()) {
            if (alt.getEmail().equalsIgnoreCase(username)) {
                getContents().remove(alt);
            }
        }
    }

}
