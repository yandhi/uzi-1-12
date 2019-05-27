package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This module is for the map "Blast Off".
 *
 * <p>
 * This module uses the Rocket Jumper Kit and automatically places the "bombs" for the player.
 * </p>
 *
 * @author Jax
 * Created in Apr 2019
 */
public class Bomber extends ToggleablePlugin {

    public Bomber() {
        super("Bomber", Category.PLAYER);
    }

    @Register
    public void onPreUpdate(EventUpdate.Pre event) {
        List<EntityPlayer> nearbyPlayers = mc.world.playerEntities.stream()
                .filter(entityPlayer -> entityPlayer != mc.player)
                .filter(entityPlayer -> mc.player.getDistanceToEntity(entityPlayer) <= 5)
                .collect(Collectors.toList());
        if (!nearbyPlayers.isEmpty()) {
            if (hasBombs() && mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() != Items.SPAWN_EGG) {
                int old = mc.player.inventory.currentItem;
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, -90, mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketHeldItemChange(getBombSlot() - 36));
                mc.playerController.updateController();
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(mc.player.getPosition().down(), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0, 0, 0));
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, mc.player.rotationPitch, mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketHeldItemChange(old));
            }
        }
    }

    private int getBombSlot() {
        for (int i = 36; i < 45; i++) {
            ItemStack stack = mc.player.inventoryContainer.getSlot(i).getStack();
            if (stack.getItem() == Items.SPAWN_EGG) {
                return i;
            }
        }
        return mc.player.inventory.currentItem;
    }

    private boolean hasBombs() {
        for (int index = 36; index < 45; index++) {
            final ItemStack stack = mc.player.inventoryContainer.getSlot(
                    index).getStack();
            if (stack.getItem() == Items.SPAWN_EGG)
                return true;
        }
        return false;
    }

}
