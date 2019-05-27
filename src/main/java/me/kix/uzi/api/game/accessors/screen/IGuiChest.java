package me.kix.uzi.api.game.accessors.screen;

import net.minecraft.inventory.IInventory;

public interface IGuiChest {

    IInventory getUpperChestInventory();

    int getInventoryRows();

}