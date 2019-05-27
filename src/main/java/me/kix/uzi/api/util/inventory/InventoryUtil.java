package me.kix.uzi.api.util.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtil {

    public static int getSlotID(Item item) {
        for (int index = 0; index <= 36; index++) {
            ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(index);
            if (stack.getItem() == Items.AIR)
                continue;
            if (stack.getItem() == item) {
                return index;
            }
        }
        return -1;
    }

}
