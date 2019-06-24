package me.kix.uzi.api.game.impl.screen;

import me.kix.uzi.api.game.accessors.screen.EditSign;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.tileentity.TileEntitySign;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author Kix
 * Created in Apr 2019
 */
@Mixin(GuiEditSign.class)
public abstract class MixinGuiEditSign implements EditSign {

    @Accessor
    @Override
    public abstract TileEntitySign getTileSign();
}
