package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class FastUse extends ToggleablePlugin {

    private final int FOOD_TICKS = 32;
    private final int DELAY = 15;

    public FastUse() {
        super("FastUse", Category.PLAYER);
        setDisplay("Fast Use");
        setColor(0xFF78C377);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        Packet p = new CPacketPlayer(mc.player.onGround);
        ItemStack stack = mc.player.getActiveItemStack();
        if (isUsable(stack) && (stack.getMaxItemUseDuration() - mc.player.getItemInUseCount()) == DELAY) {
            int neededPackets = FOOD_TICKS - DELAY;
            for(int i = 0; i < neededPackets; i++)
                mc.player.connection.sendPacket(p);
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0,0,0), EnumFacing.fromAngle(-1)));
            mc.player.stopActiveHand();
        }
    }

    private boolean isUsable(ItemStack stack) {
        if (stack == null)
            return false;
        if (mc.player.isHandActive()) {
            return (stack.getItem() instanceof ItemFood) || (stack.getItem() instanceof ItemPotion) || stack.getItem() instanceof ItemBucketMilk;
        }
        return false;
    }

}
