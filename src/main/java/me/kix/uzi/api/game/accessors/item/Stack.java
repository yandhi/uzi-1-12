package me.kix.uzi.api.game.accessors.item;

/**
 * The accessor for a standard item stack.
 *
 * @author Kix
 * @since 7/8/2019
 */
public interface Stack {

    /**
     * @return The durability of the stack.
     */
    int getTrueDurability();
}
