package me.kix.uzi.api.connection;

/**
 * An object for strategizing connections.
 *
 * @author Kix
 * @since April 2019
 */
public interface ConnectionStrategy {

    /**
     * Connects to the given URL and returns the input stream as a string.
     *
     * @param link The URL being connected to.
     */
    String connect(String link);
}