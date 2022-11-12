package me.kix.uzi.api.keybind.manage;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import me.kix.uzi.Uzi;
import me.kix.uzi.api.keybind.Keybind;
import me.kix.uzi.api.keybind.task.KeybindTaskStrategy;
import me.kix.uzi.api.keybind.task.tasks.SendMessageKeybindTaskStrategy;
import me.kix.uzi.api.keybind.task.tasks.TogglePluginKeybindTaskStrategy;
import me.kix.uzi.api.manager.ListManager;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class KeybindManager extends ListManager<Keybind> {

    /**
     * The instance of google's GSON in constant form.
     */
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * The file we are modifying on startup and shutdown.
     */
    private final Path bindsFile;

    public KeybindManager(Path directory) {
        this.bindsFile = Paths.get(directory.toString() + File.separator + "keybinds.json").toAbsolutePath();
    }

    /**
     * Initializes the keybinds/
     */
    public void init() {
        try {
            if (!Files.exists(bindsFile)) {
                Files.createFile(bindsFile);
                save();
                return;
            }
            load();
        } catch (IOException ignored) {

        }
    }

    /**
     * Loads keybinds from the file.
     */
    public void load() {
        try {
            final JsonObject object = new JsonParser().parse(new JsonReader(Files.newBufferedReader(bindsFile))).getAsJsonObject();
            final Set<Map.Entry<String, JsonElement>> elements = object.entrySet();
            elements.forEach(element -> {
                JsonObject jsonObject = element.getValue().getAsJsonObject();
                String key = jsonObject.get("Key").getAsString();
                JsonObject strategy = jsonObject.get("Strategy").getAsJsonObject();
                String type = strategy.get("Type").getAsString();
                KeybindTaskStrategy taskStrategy = null;

                switch (type.toLowerCase()) {
                    case "toggle":
                        Optional<Plugin> foundPlugin = Uzi.INSTANCE.getPluginManager().getPlugin(strategy.get("Execution").getAsString());

                        if (foundPlugin.isPresent() && foundPlugin.get() instanceof ToggleablePlugin) {
                            taskStrategy = new TogglePluginKeybindTaskStrategy((ToggleablePlugin) foundPlugin.get());
                        }
                        break;
                    case "message":
                        taskStrategy = new SendMessageKeybindTaskStrategy(strategy.get("Execution").getAsString());
                        break;
                }
                if (taskStrategy != null) {
                    getContents().add(new Keybind(element.getKey(), key, taskStrategy));
                }
            });
        } catch (IOException e) {
        }
    }

    /**
     * Saves keybinds to the file.
     */
    public void save() {
        try {
            final JsonObject object = new JsonObject();

            getContents().forEach(keybind -> {
                final JsonObject keybindObject = new JsonObject();
                final JsonObject strategy = new JsonObject();
                keybindObject.addProperty("Key", keybind.getKey());

                strategy.addProperty("Type", keybind.getTaskStrategy().getStrategy());

                if (keybind.getTaskStrategy() instanceof TogglePluginKeybindTaskStrategy) {
                    TogglePluginKeybindTaskStrategy pluginKeybindTaskStrategy = (TogglePluginKeybindTaskStrategy) keybind.getTaskStrategy();
                    strategy.addProperty("Execution", pluginKeybindTaskStrategy.getPlugin().getLabel());
                } else if (keybind.getTaskStrategy() instanceof SendMessageKeybindTaskStrategy) {
                    SendMessageKeybindTaskStrategy sendMessageKeybindTaskStrategy = (SendMessageKeybindTaskStrategy) keybind.getTaskStrategy();
                    strategy.addProperty("Execution", sendMessageKeybindTaskStrategy.getMessage());
                }
                keybindObject.add("Strategy", strategy);

                object.add(keybind.getLabel(), keybindObject);
            });
            Files.write(bindsFile, GSON.toJson(object).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a keybind based on the provided name.
     *
     * @param identifier The name of the keybind being searched for.
     * @return The {@link Optional} instance of the keybind.
     */
    public Optional<Keybind> getKeybind(String identifier) {
        return getContents()
                .stream()
                .filter(keybind -> keybind.getLabel().equalsIgnoreCase(identifier))
                .findFirst();
    }
}
