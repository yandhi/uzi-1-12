package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.screen.IGuiEditSign;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

/**
 * Allows us to add colored text to a sign.
 *
 * @author Jax
 * Created in Apr 2019
 */
public class ColorSigns extends ToggleablePlugin {

    public ColorSigns() {
        super("ColorSigns", Category.MISCELLANEOUS);
        setDisplay("Color Signs");
    }

    @Register
    public void onUpdate(EventUpdate.Pre pre) {
        if (mc.currentScreen instanceof GuiEditSign) {
            GuiEditSign editSign = (GuiEditSign) mc.currentScreen;
            TileEntitySign sign = ((IGuiEditSign) editSign).getTileSign();

            for (int i = 0; i <= 3; i++) {
                ITextComponent line = sign.signText[i];

                if (line.getFormattedText().contains("&")) {
                    sign.signText[i] = new TextComponentString(line.getFormattedText().replace("&", "\247\247"));
                }
            }
        }
    }

}
