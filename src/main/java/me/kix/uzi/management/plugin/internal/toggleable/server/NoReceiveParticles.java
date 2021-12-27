package me.kix.uzi.management.plugin.internal.toggleable.server;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import net.minecraft.network.play.server.SPacketParticles;

/**
 * Prevents the player from receiving particles.
 *
 * @author Kix
 * @since 7/2/2019
 */
public class NoReceiveParticles extends ToggleablePlugin {

    public NoReceiveParticles() {
        super("NoReceiveParticles", Category.MISCELLANEOUS);
        setDisplay("No Receive Particles");
    }

    @Register
    public void onReadPacket(EventPacket.Read read) {
        if (read.getPacket() instanceof SPacketParticles) {
            read.setCancelled(true);
        }
    }
}
