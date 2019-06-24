package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.logging.Logger;
import me.kix.uzi.management.event.entity.EventEntityChunk;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Warns the player when someone enters their render distance.
 * 
 * @author Kix
 * @since April 2019
 */
public class Doorbell extends ToggleablePlugin {

    public Doorbell() {
        super("Doorbell", Category.MISCELLANEOUS);
    }

    @Register
    public void onEntityEnterChunk(EventEntityChunk.Enter enter) {
        if (enter.getEntity() instanceof EntityPlayer) {
            Logger.printMessage("Ding dong! " + enter.getEntity().getName() + " has entered your view distance!");
        }
    }

    @Register
    public void onEntityLeaveChunk(EventEntityChunk.Leave leave) {
        if (leave.getEntity() instanceof EntityPlayer) {
            Logger.printMessage("Ding dong! " + leave.getEntity().getName() + " has left your view distance!");
        }
    }
}