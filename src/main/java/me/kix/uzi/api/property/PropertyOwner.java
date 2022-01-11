package me.kix.uzi.api.property;

import java.util.List;
import java.util.Optional;

/**
 * Defines a class as being an owner of properties.
 *
 * @author jackson
 * @since 1/11/2022
 */
public interface PropertyOwner {

    /**
     * @return The properties
     */
    List<Property> getProperties();

    /**
     * Allows us to gain access to specified properties based on a given term.
     *
     * @param term The name of the property being searched for.
     * @return The {@link Optional} instance of the property.
     */
    default Optional<Property> getProperty(String term) {
        for (Property property : getProperties()) {
            if (property.getLabel().equalsIgnoreCase(term)) {
                return Optional.of(property);
            }
        }
        return Optional.empty();
    }
}
