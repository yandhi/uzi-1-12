package me.kix.uzi.management.ui.alt.manage;

import com.google.gson.*;
import me.kix.uzi.api.manager.ListManager;
import me.kix.uzi.management.ui.alt.Alt;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

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
            getContents().forEach(alt -> object.add(alt.getUsername(), new JsonPrimitive(alt.getPassword())));
            writer.print(GSON.toJson(object));
            writer.close();
        } catch (FileNotFoundException ignored) {

        }
    }

    public void load() {
        try {
            final JsonObject object = new JsonParser().parse(new FileReader(altsFile)).getAsJsonObject();
            final Set<Map.Entry<String, JsonElement>> elements = object.entrySet();
            elements.forEach(entry -> getContents().add(new Alt(entry.getKey(), entry.getValue().getAsString())));
        } catch (FileNotFoundException ignored) {
        }
    }

    public void remove(String username) {
        for (Alt alt : getContents()) {
            if (alt.getUsername().equalsIgnoreCase(username)) {
                getContents().remove(alt);
            }
        }
    }

}
