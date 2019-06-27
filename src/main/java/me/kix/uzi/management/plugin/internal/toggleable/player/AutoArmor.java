package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.inventory.InventoryUtil;
import me.kix.uzi.api.util.math.timing.Timer;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;

public class AutoArmor extends ToggleablePlugin {

    private final Item[] helmets = {Items.DIAMOND_HELMET, Items.IRON_HELMET, Items.GOLDEN_HELMET, Items.CHAINMAIL_HELMET, Items.LEATHER_HELMET};
    private final Item[] chestplates = {Items.DIAMOND_CHESTPLATE, Items.IRON_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.LEATHER_CHESTPLATE};
    private final Item[] leggings = {Items.DIAMOND_LEGGINGS, Items.IRON_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.CHAINMAIL_LEGGINGS, Items.LEATHER_LEGGINGS};
    private final Item[] boots = {Items.DIAMOND_BOOTS, Items.IRON_BOOTS, Items.GOLDEN_BOOTS, Items.CHAINMAIL_BOOTS, Items.LEATHER_BOOTS};
    private final Timer timer = new Timer();

    public AutoArmor() {
        super("AutoArmor", Category.PLAYER);
        setDisplay("Auto Armor");
        setColor(0xE6C28B);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        int selectedSlotId = -1;
        if (timer.completed(100)) {
            if (mc.player.inventory.armorItemInSlot(2).getItem() == Items.AIR) {
                for (Item item : chestplates) {
                    int slotId = InventoryUtil.getSlotID(item);
                    if (slotId != -1) {
                        selectedSlotId = slotId;
                    }
                }
            }

            if (mc.player.inventory.armorItemInSlot(1).getItem() == Items.AIR) {
                for (Item item : leggings) {
                    int slotId = InventoryUtil.getSlotID(item);
                    if (slotId != -1) {
                        selectedSlotId = slotId;
                    }
                }
            }

            if (mc.player.inventory.armorItemInSlot(0).getItem() == Items.AIR) {
                for (Item item : boots) {
                    int slotId = InventoryUtil.getSlotID(item);
                    if (slotId != -1) {
                        selectedSlotId = slotId;
                    }
                }
            }

            if (mc.player.inventory.armorItemInSlot(3).getItem() == Items.AIR) {
                for (Item item : helmets) {
                    int slotId = InventoryUtil.getSlotID(item);
                    if (slotId != -1) {
                        selectedSlotId = slotId;
                    }
                }
            }

            if (selectedSlotId != -1) {
                if (selectedSlotId < 9)
                    selectedSlotId += 36;
                mc.playerController.windowClick(0, selectedSlotId, 0, ClickType.QUICK_MOVE, mc.player);
                timer.reset();
            }
        }

    }

}
