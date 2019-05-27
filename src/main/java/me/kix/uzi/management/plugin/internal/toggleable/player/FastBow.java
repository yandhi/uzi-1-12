package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.item.*;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class FastBow extends ToggleablePlugin {

    private final int BOW_TICKS = 20;
    private final int DELAY = 15;

    public FastBow() {
        super("FastBow", Category.PLAYER);
        setDisplay("Fast Bow");
        setColor(0xFFE8D473);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        Packet p = new CPacketPlayer(mc.player.onGround);
        ItemStack stack = mc.player.getActiveItemStack();
        if (isUsable(stack) && (stack.getMaxItemUseDuration() - mc.player.getItemInUseCount()) == DELAY) {
            int neededPackets = BOW_TICKS - DELAY;
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
            return (stack.getItem() instanceof ItemBow);
        }
        return false;
    }
}
