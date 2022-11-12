package me.kix.uzi.management.plugin.internal.toggleable.qol;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.screen.EditSign;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

/**
 * Allows us to add colored text to a sign.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class ColorSigns extends ToggleablePlugin {

    public ColorSigns() {
        super("ColorSigns", Category.QOL);
        setDisplay("Color Signs");
    }

    @Register
    public void onUpdate(EventUpdate.Pre pre) {
        if (mc.currentScreen instanceof GuiEditSign) {
            GuiEditSign editSign = (GuiEditSign) mc.currentScreen;
            TileEntitySign sign = ((EditSign) editSign).getTileSign();

            for (int i = 0; i <= 3; i++) {
                ITextComponent line = sign.signText[i];

                if (line.getFormattedText().contains("&")) {
                    sign.signText[i] = new TextComponentString(line.getFormattedText().replace("&", "\247\247"));
                }
            }
        }
    }
}
