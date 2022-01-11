package me.kix.uzi.api.plugin;

import com.google.gson.JsonObject;
import me.kix.uzi.api.property.PropertyOwner;
import me.kix.uzi.api.util.config.Configurable;
import me.kix.uzi.api.util.interfaces.Labeled;
import me.kix.uzi.api.util.interfaces.MinecraftAccessor;

/**
 * The base for all "parts" of the client.
 *
 * @author jackson
 * @since 1/11/2022
 */
public interface Plugin extends Labeled, PropertyOwner, Configurable<JsonObject>, MinecraftAccessor {

    /**
     * Initializes the plugin;
     */
    void initPlugin();

    /**
     * @return The category that a plugin is a part of.
     */
    Category getCategory();
}
