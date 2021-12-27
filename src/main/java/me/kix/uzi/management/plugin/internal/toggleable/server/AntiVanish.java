package me.kix.uzi.management.plugin.internal.toggleable.server;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.logging.Logger;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import net.minecraft.network.play.server.SPacketPlayerListItem;

/**
 * Prevents server admins from going into vanish without the user knowing.
 * 
 * @author Kix
 * @since April 2019
 */
public class AntiVanish extends ToggleablePlugin {

    public AntiVanish() {
        super("AntiVanish", Category.SERVER);
        setDisplay("Anti Vanish");
    }

    @Register
    public void onReadPacket(EventPacket.Read read) {
        if (read.getPacket() instanceof SPacketPlayerListItem) {
            SPacketPlayerListItem tabList = (SPacketPlayerListItem) read.getPacket();
            for (SPacketPlayerListItem.AddPlayerData playerData : tabList.getEntries()) {
                if (mc.getConnection().getPlayerInfo(playerData.getProfile().getId()) == null && playerData.getProfile().getName() != null) {
					Logger.printMessage(playerData.getProfile().getName() + " has gone into vanish!");
				}
            }
        }
    }

}