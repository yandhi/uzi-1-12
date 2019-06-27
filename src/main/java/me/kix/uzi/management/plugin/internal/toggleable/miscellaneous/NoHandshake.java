package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import net.minecraft.network.status.client.CPacketServerQuery;

/**
 * Prevents forge from giving out the player's mod list.
 *
 * @author Kix
 * Created in 06 2019.
 */
public class NoHandshake extends ToggleablePlugin {

    public NoHandshake() {
        super("NoHandshake", Category.MISCELLANEOUS);
        setDisplay("No Handshake");
    }

    @Register
    public void onSendPacket(EventPacket.Send sendPacket) {
        if (sendPacket.getPacket() instanceof CPacketServerQuery) {
            sendPacket.setCancelled(true);
        }
    }

}
