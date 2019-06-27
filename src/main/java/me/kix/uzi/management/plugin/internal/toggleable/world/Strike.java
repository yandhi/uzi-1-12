package me.kix.uzi.management.plugin.internal.toggleable.world;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.logging.Logger;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;

/**
 * Alerts the player whenever lightning occurs.
 *
 * <p>
 * This is used for the extremely old lightning exploit.
 * </p>
 *
 * @author Kix
 * Created in 06 2019.
 */
public class Strike extends ToggleablePlugin {

    public Strike() {
        super("Strike", Category.WORLD);
    }

    @Register
    public void onReadPacket(EventPacket.Read read) {
        if (read.getPacket() instanceof SPacketSpawnGlobalEntity) {
            SPacketSpawnGlobalEntity packetSpawnGlobalEntity = (SPacketSpawnGlobalEntity) read.getPacket();

            if (packetSpawnGlobalEntity.getType() == 1) {
                Logger.printMessage("A strike has occurred at " + String.format("%s, %s, %s.", Math.round(packetSpawnGlobalEntity.getX()),
                        Math.round(packetSpawnGlobalEntity.getY()), Math.round(packetSpawnGlobalEntity.getZ())));
            }
        }
    }

}
