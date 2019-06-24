package me.kix.uzi.api.plugin.toggleable;

import com.google.gson.JsonObject;
import me.kix.uzi.Uzi;
import me.kix.uzi.api.keybind.task.tasks.TogglePluginKeybindTaskStrategy;
import me.kix.uzi.api.keybind.Keybind;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.Plugin;

import java.awt.*;
import java.util.Random;

public class ToggleablePlugin extends Plugin implements Toggleable {

    private boolean enabled, hidden;
    private int color;
    protected final Random random = new Random();
    private String display;

    public ToggleablePlugin(String label, Category category) {
        super(label, category);
        this.display = label;
        this.color = Color.getHSBColor(random.nextFloat(), 0.7F, 0.7F).getRGB();
    }

    @Override
    public void toggle() {
        enabled = !enabled;
        if (enabled) {
            onEnable();
        }else {
            onDisable();
        }
    }

    @Override
    public void save(JsonObject destination) {
        super.save(destination);
        String facet = "NONE";
        for (Keybind keybind : Uzi.INSTANCE.getKeybindManager().getContents()) {
            if (keybind.getTaskStrategy() instanceof TogglePluginKeybindTaskStrategy && ((TogglePluginKeybindTaskStrategy) keybind.getTaskStrategy()).getPlugin() == this) {
                facet = keybind.getKey();
            }
        }
        destination.addProperty("Keybind", facet);
        destination.addProperty("Hidden", hidden);
        destination.addProperty("Enabled", enabled);
    }

    @Override
    public void load(JsonObject source) {
        super.load(source);
        source.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase("Keybind"))
                .filter(entry -> !entry.getValue().getAsString().equalsIgnoreCase("NONE"))
                .forEach(entry -> Uzi.INSTANCE.getKeybindManager().getContents().add(new Keybind(getLabel(), entry.getValue().getAsString().toUpperCase(), new TogglePluginKeybindTaskStrategy(this))));
        source.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase("Enabled"))
                .filter(entry -> entry.getValue().getAsBoolean())
                .forEach(entry -> toggle());
        source.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase("Hidden"))
                .forEach(entry -> setHidden(entry.getValue().getAsBoolean()));
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void onEnable() {
        Uzi.INSTANCE.getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        Uzi.INSTANCE.getEventManager().unregister(this);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
