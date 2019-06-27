package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import net.minecraft.network.play.client.CPacketCloseWindow;

/**
 * Allows the player to hold items in their crafting menu in their inventory.
 *
 * @author Kix
 * @since 6/27/2019
 */
public class XCarry extends ToggleablePlugin {

    public XCarry() {
        super("XCarry", Category.PLAYER);
        setDisplay("X-Carry");
    }

    @Register
    public void onSendPacket(EventPacket.Send send) {
        if (send.getPacket() instanceof CPacketCloseWindow) {
            send.setCancelled(true);
        }
    }

}
