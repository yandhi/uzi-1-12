package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.entity.Player;
import me.kix.uzi.api.game.accessors.packet.PacketPlayer;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * A simple jesus hack.
 *
 * @author Kix
 * @since June 21, 2019
 */
public class Jesus extends ToggleablePlugin {

    public Jesus() {
        super("Jesus", Category.MOVEMENT);
        setColor(0xFFA0D7FF);
    }

    @Register
    public void onPreUpdate(EventUpdate.Pre preUpdate) {
        Player player = (Player) mc.player;
        if (player.isInLiquid() && !mc.player.isSneaking()) {
            if (mc.gameSettings.keyBindJump.isPressed()) {
                /* Lets us jump. */
                mc.player.motionY = 0.085;
            } else {
                /* The normal swim value. */
                mc.player.motionY = 0.1;
            }
        }
    }

    @Register
    public void onSendPacket(EventPacket.Send send) {
        if (send.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer packetPlayer = (CPacketPlayer) send.getPacket();
            PacketPlayer mixinPacketPlayer = (PacketPlayer) packetPlayer;
            EntityPlayerSP player = mc.player;
            Player mixinPlayer = (Player) mc.player;

            if (mixinPlayer.isOnLiquid() && !mixinPlayer.isInLiquid() && mc.player.fallDistance <= 3 && !mc.gameSettings.keyBindSneak.isPressed()) {
                /* Offset our value in order to bypass NoCheatPlus. */
                mixinPacketPlayer.setY(player.posY + (player.ticksExisted % 2 == 0 ? 0.1 : -0.1));
            }
        }
    }

}
