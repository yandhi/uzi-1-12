package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.logging.Logger;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import net.minecraft.network.play.server.SPacketEntityTeleport;

/**
 * I believe this is the dumb exploit used by jared2013 and the other 2b2t idiots in order to trace base coords.
 *
 * @author Kix
 * Created in 06 2019.
 */
public class PearlLogger extends ToggleablePlugin {

    public PearlLogger() {
        super("PearlLogger", Category.MISCELLANEOUS);
        setDisplay("Pearl Logger");
    }

    @Register
    public void onReadPacket(EventPacket.Read read) {
        if (read.getPacket() instanceof SPacketEntityTeleport) {
            SPacketEntityTeleport packetEntityTeleport = new SPacketEntityTeleport();
            if (packetEntityTeleport.getEntityId() != mc.player.getEntityId()) {
                Logger.printMessage(String.format("%s, %S, %s.", Math.round(packetEntityTeleport.getX()), Math.round(packetEntityTeleport.getY()), Math.round(packetEntityTeleport.getZ())));
            }
        }
    }
}
