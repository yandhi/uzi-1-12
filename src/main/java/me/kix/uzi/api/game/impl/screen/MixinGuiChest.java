package me.kix.uzi.api.game.impl.screen;

import me.kix.uzi.api.game.accessors.screen.Chest;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.IInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiChest.class)
public abstract class MixinGuiChest implements Chest {

    @Override
    @Accessor
    public abstract IInventory getUpperChestInventory();

    @Override
    @Accessor
    public abstract int getInventoryRows();
}
