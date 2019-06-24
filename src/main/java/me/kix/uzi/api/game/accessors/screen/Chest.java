package me.kix.uzi.api.game.accessors.screen;

import net.minecraft.inventory.IInventory;

/**
 * The gui screen for a chest.
 *
 * @author Kix
 * @since June 2018.
 */
public interface Chest {

    /**
     * @return The chest's inventory.
     */
    IInventory getUpperChestInventory();

    /**
     * @return The amount of rows in the chest.
     */
    int getInventoryRows();
}