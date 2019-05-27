package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.entity.IPlayerSP;
import me.kix.uzi.api.game.accessors.packet.ICPacketPlayer;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.block.EventBoundingBox;
import me.kix.uzi.management.event.entity.EventUpdate;
import me.kix.uzi.management.event.input.packet.EventPacket;
import net.minecraft.block.BlockLiquid;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * Bob jesus.
 * @author Kix
 */
public class Jesus extends ToggleablePlugin {

    public Jesus() {
        super("Jesus", Category.MOVEMENT);
        setColor(0xFFA0D7FF);
    }

    @Register
    public void onPreUpdate(EventUpdate.Pre event) {
        if (((IPlayerSP) mc.player).isInLiquid() && !mc.player.isSneaking() && !mc.gameSettings.keyBindJump.isPressed())
            mc.player.motionY = 0.1;
    }

    @Register
    public void onSendPacket(EventPacket.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            if (((IPlayerSP) mc.player).isOnLiquid())
                ((ICPacketPlayer) event.getPacket()).setY(mc.player.posY + (mc.player.ticksExisted % 2 == 0 ? 0.01 : -0.01));
        }
    }

}
