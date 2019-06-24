package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;

/**
 * Prevents us from being book banned.
 * Automatically drops any books in our inventory.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class AntiBookBan extends ToggleablePlugin {

    public AntiBookBan() {
        super("AntiBookBan", Category.MISCELLANEOUS);
        setDisplay("Anti Book Ban");
    }

    @Register
    public void onUpdate(EventUpdate.Pre pre) {
        for (int i = 0; i <= 45; i++) {
            ItemStack item = mc.player.inventory.getStackInSlot(i);
            if (item.getItem() instanceof ItemBook) {
                mc.player.dropItem(item, false);
            }
        }
    }
}
