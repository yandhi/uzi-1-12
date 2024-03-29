package me.kix.uzi.management.plugin.internal.toggleable.qol;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.screen.EditSign;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.TextComponentString;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Stamps the signature and date of the player on signs that they edit.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class Stamp extends ToggleablePlugin {

    public Stamp() {
        super("Stamp", Category.QOL);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        if (mc.currentScreen instanceof GuiEditSign) {
            GuiEditSign editSign = (GuiEditSign) mc.currentScreen;
            TileEntitySign sign = ((EditSign) editSign).getTileSign();
            Date date = new Date();
            DateFormat dateFormatter = new SimpleDateFormat("d/M/YYYY");
            sign.signText[2] = new TextComponentString(mc.player.getName());
            sign.signText[3] = new TextComponentString(dateFormatter.format(date));
        }
    }
}
