package me.kix.uzi.api.util.config;

/**
 * Marks an object as being able to be configured based on type.
 *
 * @param <T> The type of configuration used.
 * @author Kix
 * @since April 2018
 */
public interface Configurable<T> {

    /**
     * Saves information about an object.
     *
     * @param destination The object that the config will push to.
     */
    void save(T destination);

    /**
     * Loads information about an object.
     *
     * @param source The source of the information being loaded.
     */
    void load(T source);

}
