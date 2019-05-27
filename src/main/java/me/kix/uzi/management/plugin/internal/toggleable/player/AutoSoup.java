package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.math.timing.Timer;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;

public class AutoSoup extends ToggleablePlugin {

    private NumberProperty<Float> health = new NumberProperty<>("Health", 18.0F, 0F, 20F);
    private NumberProperty<Integer> delay = new NumberProperty<>("Delay", 500, 0, 1000);
    private final Timer timer;

    public AutoSoup() {
        super("AutoSoup", Category.PLAYER);
        setColor(0xFF4EC078);
        timer = new Timer();
        getProperties().add(health);
        getProperties().add(delay);
        setDisplay("Auto Soup");
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        if (updateCounter() == 0)
            return;
        if (mc.player.getHealth() <= health.getValue() && timer.completed(delay.getValue())) {
            if (doesHotbarHaveSoups()) {
                eatSoup();
            } else {
                getSoupFromInventory();
            }
            timer.reset();
        }
    }

    private boolean doesHotbarHaveSoups() {
        for (int index = 36; index < 45; index++) {
            final ItemStack stack = mc.player.inventoryContainer.getSlot(
                    index).getStack();
            if (isStackSoup(stack))
                return true;
        }
        return false;
    }

    private void eatSoup() {
        for (int index = 36; index < 45; index++) {
            final ItemStack stack = mc.player.inventoryContainer.getSlot(
                    index).getStack();
            if (isStackSoup(stack)) {
                stackBowls();
                final int oldslot = mc.player.inventory.currentItem;
                mc.getConnection().sendPacket(
                        new CPacketHeldItemChange(index - 36));
                mc.playerController.updateController();
                mc.getConnection().sendPacket(
                        new CPacketPlayerTryUseItem());
                mc.getConnection().sendPacket(
                        new CPacketHeldItemChange(oldslot));
                mc.getConnection().sendPacket(
                        new CPacketPlayer.Rotation(
                                mc.player.rotationYaw,
                                mc.player.rotationPitch,
                                mc.player.onGround));
                break;
            }
        }
    }

    private void getSoupFromInventory() {
        if (mc.currentScreen instanceof GuiChest)
            return;
        stackBowls();
        for (int index = 9; index < 36; index++) {
            final ItemStack stack = mc.player.inventoryContainer.getSlot(
                    index).getStack();
            if (isStackSoup(stack)) {
                mc.playerController.windowClick(0, index, 0, ClickType.SWAP, mc.player);
                break;
            }
        }
    }

    private boolean isStackSoup(ItemStack stack) {
        if (stack == null)
            return false;
        return stack.getItem() instanceof ItemSoup;
    }

    private void stackBowls() {
        if (mc.currentScreen instanceof GuiChest)
            return;
        for (int index = 9; index < 45; index++) {
            final ItemStack stack = mc.player.inventoryContainer.getSlot(
                    index).getStack();
            if (stack == null) {
                continue;
            }

            if (stack.getItem() == Items.BOWL) {
                mc.playerController.windowClick(0, index, 0, ClickType.THROW, mc.player);
                break;
            }
        }
    }

    private int updateCounter() {
        int counter = 0;
        for (int index = 9; index < 45; index++) {
            final ItemStack stack = mc.player.inventoryContainer.getSlot(
                    index).getStack();
            if (isStackSoup(stack)) {
                counter += stack.getMaxStackSize();
            }
        }
        return counter;
    }

}
