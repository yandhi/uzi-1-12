package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.input.packet.EventPacket;
import net.minecraft.network.play.client.CPacketConfirmTeleport;

/**
 * We become untouchable after going through a portal.
 *
 * @author Jax
 * Created in Apr 2019
 */
public class PortalGodMode extends ToggleablePlugin {

    public PortalGodMode() {
        super("PortalGodMode", Category.MISCELLANEOUS);
        setDisplay("Portal God Mode");
    }

    @Register
    public void onSendPacket(EventPacket.Send send) {
        if (send.getPacket() instanceof CPacketConfirmTeleport) {
            send.setCancelled(true);
        }
    }
}
