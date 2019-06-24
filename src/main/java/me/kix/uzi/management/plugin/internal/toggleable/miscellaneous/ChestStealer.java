package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.screen.Chest;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.math.timing.Timer;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;

public class ChestStealer extends ToggleablePlugin {

    private final Timer timer = new Timer();

    public ChestStealer() {
        super("ChestStealer", Category.MISCELLANEOUS);
        setDisplay("Chest Stealer");
        setColor(0xE6829C);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        if (mc.currentScreen instanceof GuiChest) {
            GuiChest chest = (GuiChest) mc.currentScreen;
            Chest mxChest = (Chest) chest;
            int rows = mxChest.getInventoryRows() * 9;
            for (int i = 0; i < rows; i++) {
                Slot slot = chest.inventorySlots.getSlot(i);
                if (slot.getHasStack()) {
                    if (timer.completed(100)) {
                        mc.playerController.windowClick(chest.inventorySlots.windowId, slot.slotNumber, 0, ClickType.QUICK_MOVE, mc.player);
                        timer.reset();
                    }
                }
            }
        }
    }

}
