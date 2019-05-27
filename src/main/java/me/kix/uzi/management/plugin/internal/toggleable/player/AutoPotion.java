package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.math.timing.Timer;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;

public class AutoPotion extends ToggleablePlugin {

    private NumberProperty<Float> health = new NumberProperty<>("Health", 18.0F, 0F, 20F);
    private NumberProperty<Integer> delay = new NumberProperty<>("Delay", 500, 0, 1000);
    private final Timer timer;

    public AutoPotion() {
        super("AutoPotion", Category.PLAYER);
        setDisplay("Auto Potion");
        setColor(0xFFF38AFF);
        timer = new Timer();
        getProperties().add(health);
        getProperties().add(delay);
    }

    @Register
    public void onPreUpdate(EventUpdate.Pre event) {
        if (updateCounter() == 0)
            return;
        if (mc.player.getHealth() <= health.getValue()
                && timer.completed(delay.getValue())) {
            if (doesHotbarHavePots()) {
                event.getViewAngles().setPitch(90);
            }
        }
    }

    @Register
    public void onPostUpdate(EventUpdate.Post event) {
        if (updateCounter() == 0)
            return;
        if (mc.player.getHealth() <= health.getValue() && timer.completed(delay.getValue())) {
            if (doesHotbarHavePots()) {
                splashPot();
            } else {
                getPotsFromInventory();
            }
            timer.reset();
        }
    }

    private boolean doesHotbarHavePots() {
        for (int index = 36; index < 45; index++) {
            final ItemStack stack = mc.player.inventoryContainer.getSlot(
                    index).getStack();
            if (isStackSplashHealthPot(stack))
                return true;
        }
        return false;
    }

    private void getPotsFromInventory() {
        if (mc.currentScreen instanceof GuiChest)
            return;
        for (int index = 9; index < 36; index++) {
            final ItemStack stack = mc.player.inventoryContainer.getSlot(
                    index).getStack();
            if (isStackSplashHealthPot(stack)) {
                mc.playerController.windowClick(0, index, 0, ClickType.SWAP, mc.player);
                break;
            }
        }
    }

    private boolean isStackSplashHealthPot(ItemStack stack) {
        if (stack.getItem() == Items.SPLASH_POTION) {
            for (PotionEffect effect : PotionUtils.getEffectsFromStack(stack))
                if (effect.getPotion() == MobEffects.INSTANT_HEALTH)
                    return true;
        }
        return false;
    }

    private void splashPot() {
        for (int index = 36; index < 45; index++) {
            final ItemStack stack = mc.player.inventoryContainer.getSlot(
                    index).getStack();
            if (isStackSplashHealthPot(stack)) {
                final int oldslot = mc.player.inventory.currentItem;
                mc.getConnection().sendPacket(
                        new CPacketPlayer.Rotation(
                                mc.player.rotationYaw, 90,
                                mc.player.onGround));
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

    private int updateCounter() {
        int counter = 0;
        for (int index = 9; index < 45; index++) {
            final ItemStack stack = mc.player.inventoryContainer.getSlot(
                    index).getStack();
            if (stack.getItem() == Items.AIR) {
                continue;
            }
            if (isStackSplashHealthPot(stack)) {
                counter += stack.getMaxStackSize();
            }
        }
        return counter;
    }

}
