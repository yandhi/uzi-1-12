package me.kix.uzi.api.util.interfaces;

/**
 * The outline for all clients utilizing the uzi-core.
 *
 * @author Kix
 * @since April 2018
 */
public interface Client {

    /**
     * Runs on the startup of the client.
     */
    void init();

    /**
     * Meant to run on the shutdown of the client or on a shutdown thread.
     */
    void shutdown();

    /**
     * Identifies the title of the client.
     *
     * @return The client title.
     */
    String getLabel();

    /**
     * Version based on any format.
     * Typically it is best to use Semantic versioning for this.
     *
     * @return The client version.
     */
    String getVersion();

    /**
     * All of the developers of the client.
     *
     * @return The client developer.
     */
    String[] getAuthors();

}
