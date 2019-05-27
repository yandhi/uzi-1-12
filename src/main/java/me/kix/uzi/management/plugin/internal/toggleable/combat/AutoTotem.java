package me.kix.uzi.management.plugin.internal.toggleable.combat;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class AutoTotem extends ToggleablePlugin {

    public AutoTotem() {
        super("AutoTotem", Category.COMBAT);
        setDisplay("Auto Totem");
        setColor(0x91E6AA);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        for (int i = 0; i < mc.player.inventory.mainInventory.size(); i++) {
            if (mc.player.inventory.mainInventory.get(i) != ItemStack.EMPTY) {
                if (mc.player.inventory.mainInventory.get(i).getItem() == Items.TOTEM_OF_UNDYING) {
                    if (mc.player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() != Items.TOTEM_OF_UNDYING) {
                        replaceTotem(i);
                        break;
                    }
                }
            }
        }
    }

    private void replaceTotem(int inventoryIndex) {
        if (mc.player.openContainer instanceof ContainerPlayer) {
            mc.playerController.windowClick(0, inventoryIndex < 9 ? inventoryIndex + 36 : inventoryIndex, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, inventoryIndex < 9 ? inventoryIndex + 36 : inventoryIndex, 0, ClickType.PICKUP, mc.player);
        }
    }

}
