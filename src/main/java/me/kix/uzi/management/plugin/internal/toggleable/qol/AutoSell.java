package me.kix.uzi.management.plugin.internal.toggleable.qol;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.misc.EventTick;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Automatically sells all the items in the player's inventory for prison servers.
 *
 * @author jackson
 * @since 12/26/2021
 */
public class AutoSell extends ToggleablePlugin {

    public AutoSell() {
        super("AutoSell", Category.QOL);
        setDisplay("Auto Sell");
    }

    @Register
    public void tick(EventTick tick) {
        if(mc.player != null) {
            if (isInventoryFull()) {
                mc.player.sendChatMessage("/sellall");
            }
        }
    }

    /**
     * @return Whether the inventory is full.
     */
    private boolean isInventoryFull() {
        boolean full = true;

        for (int i = 0; i < 36; i++) {
            ItemStack item = mc.player.inventory.getStackInSlot(i);

            if (item.getItem() == Items.AIR) {
                full = false;
            }
        }

        return full;
    }
}
