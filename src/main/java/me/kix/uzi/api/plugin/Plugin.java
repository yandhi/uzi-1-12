package me.kix.uzi.api.plugin;

import com.google.gson.JsonObject;
import me.kix.uzi.api.command.parsing.parsers.BooleanParser;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.config.Configurable;
import me.kix.uzi.api.util.interfaces.Labeled;
import me.kix.uzi.api.util.interfaces.MinecraftAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Plugin implements Labeled, Configurable<JsonObject>, MinecraftAccessor {

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

    public Plugin(String label, Category category) {
        this.label = label;
        this.category = category;
    }

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

    public List<Property> getProperties() {
        return properties;
    }

    /**
     * Allows us to gain access to specified properties based on a given term.
     *
     * @param term The name of the property being searched for.
     * @return The {@link Optional} instance of the property.
     */
    public Optional<Property> getProperty(String term) {
        for (Property property : getProperties()) {
            if (property.getLabel().equalsIgnoreCase(term)) {
                return Optional.of(property);
            }
        }
        return Optional.empty();
    }
}
