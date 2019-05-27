package me.kix.uzi.api.plugin;

import com.google.gson.JsonObject;
import me.kix.uzi.api.command.parsing.parsers.BooleanParser;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.interfaces.Configurable;
import me.kix.uzi.api.util.interfaces.Labeled;
import me.kix.uzi.api.util.interfaces.MinecraftAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Plugin implements Labeled, Configurable<JsonObject>, MinecraftAccessor {

    private final String label;
    private final Category category;
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

    public Optional<Property> getProperty(String term) {
        for (Property property : getProperties()) {
            if (property.getLabel().equalsIgnoreCase(term)) {
                return Optional.of(property);
            }
        }
        return Optional.empty();
    }
}
