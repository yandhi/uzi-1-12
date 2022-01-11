package me.kix.uzi.api.plugin.toggleable;

import com.google.gson.JsonObject;
import me.kix.uzi.Uzi;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.AbstractPlugin;

import java.awt.*;
import java.util.Random;

/**
 * An implementation of {@link me.kix.uzi.api.plugin.Plugin} that features the ability to be toggled from {@link Toggleable}.
 *
 * @author jackson
 * @since 2018 (revised/docs wrote 2022)
 */
public class ToggleablePlugin extends AbstractPlugin implements Toggleable {

    /**
     * The state of the plugin.
     */
    private boolean enabled;

    /**
     * Whether the plugin is to be shown in the array list.
     */
    private boolean hidden;

    /**
     * The color of the plugin.
     */
    private int color;

    /**
     * An instance of random for random color generation on class creation.
     */
    protected final Random random = new Random();

    /**
     * The display name of the plugin in the array list.
     */
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
        } else {
            onDisable();
        }
    }

    @Override
    public void save(JsonObject destination) {
        super.save(destination);
        destination.addProperty("Hidden", hidden);
        destination.addProperty("Enabled", enabled);
    }

    @Override
    public void load(JsonObject source) {
        super.load(source);
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
