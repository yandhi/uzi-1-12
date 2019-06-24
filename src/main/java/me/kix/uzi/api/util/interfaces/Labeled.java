package me.kix.uzi.api.util.interfaces;

/**
 * Marks an object as being labeled.
 *
 * <p>
 * By default, this interface is immutable.
 * However, there is a mutable child class in order to allow for label mutation.
 * </p>
 *
 * @author Kix
 * @since April 2018.
 */
public interface Labeled {

    /**
     * @return The name of the object.
     */
    String getLabel();

    /**
     * A mutable form of {@link Labeled}.
     *
     * <p>
     * The main difference is that this implementation can be changed.
     * </p>
     */
    interface Mutable extends Labeled {

        /**
         * Changes the name of the object.
         *
         * <p>
         * This is what makes the named object mutable.
         * </p>
         *
         * @param label The new name of the object.
         */
        void setLabel(String label);
    }

}
