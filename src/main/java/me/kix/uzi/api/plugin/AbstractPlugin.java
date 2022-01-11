package me.kix.uzi.api.plugin;

import com.google.gson.JsonObject;
import me.kix.uzi.api.command.parsing.parsers.BooleanParser;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.PropertyOwner;
import me.kix.uzi.api.property.properties.EnumProperty;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.config.Configurable;
import me.kix.uzi.api.util.interfaces.Labeled;
import me.kix.uzi.api.util.interfaces.MinecraftAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The outline for all implementations of {@link Plugin}.
 *
 * @author Jacks
 * @since 2018, revised 2022.
 */
public class AbstractPlugin implements Plugin {

    /**
     * The name of the plugin.
     */
    private final String label;

    /**
     * The category that the plugin falls under.
     */
    private final Category category;

    /**
     * The collection of properties that the plugin has.
     */
    private final List<Property> properties = new ArrayList<>();

    public AbstractPlugin(String label, Category category) {
        this.label = label;
        this.category = category;
    }

    /**
     * Initializes the plugin;
     */
    @Override
    public void initPlugin() {}

    @Override
    public void save(JsonObject destination) {
        if (!properties.isEmpty())
            properties.forEach(property -> destination.addProperty(property.getLabel(), property.getValue().toString()));
    }

    @Override
    public void load(JsonObject source) {
        if (!source.entrySet().isEmpty()) {
            source.entrySet().stream().filter(entry -> getProperty(entry.getKey()).isPresent()).forEach(entry -> {
                Property property = getProperty(entry.getKey()).get();
                if (property.getValue() instanceof Boolean) {
                    final BooleanParser parser = new BooleanParser();
                    property.setValue(parser.parse(entry.getValue().getAsString()));
                } else if (property instanceof NumberProperty) {
                    ((NumberProperty) property).setValue(entry.getValue().getAsString());
                } else if (property instanceof EnumProperty) {
                    ((EnumProperty) property).setValue(entry.getValue().getAsString());
                }
            });
        }
    }

    @Override
    public String getLabel() {
        return label;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public List<Property> getProperties() {
        return properties;
    }
}
