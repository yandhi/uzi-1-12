package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.entity.Player;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows the player to "blink" through blocks.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class Blink extends ToggleablePlugin {

    /**
     * The packets to be sent.
     */
    private final List<Packet> queuedPackets = new ArrayList<>();

    /**
     * The entity that "pretends" to be us.
     */
    private EntityOtherPlayerMP dummy;

    public Blink() {
        super("Blink", Category.MOVEMENT);
    }

    @Register
    public void onPacketSend(EventPacket.Send send) {
        if (send.getPacket() instanceof CPacketPlayer) {
            Player player = (Player) mc.player;
            if (player.isMoving() || !((mc.player.posY - mc.player.lastTickPosY) == 0.0D)) {
                queuedPackets.add(send.getPacket());
            }
        } else {
            queuedPackets.add(send.getPacket());
        }
        send.setCancelled(true);
    }

    /**
     * Polls the queue of packets.
     */
    private void pollPackets() {
        queuedPackets.forEach(packet -> mc.getConnection().sendPacket(packet));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        dummy = new EntityOtherPlayerMP(mc.world, mc.player.getGameProfile());
        dummy.copyLocationAndAnglesFrom(mc.player);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        pollPackets();
        queuedPackets.clear();
    }
}
