package me.kix.uzi.api.game.accessors.screen;

import net.minecraft.tileentity.TileEntitySign;

/**
 * @author Jax
 * Created in Apr 2019
 */
public interface IGuiEditSign {

    /**
     * @return The instance of the sign being edited.
     */
    TileEntitySign getTileSign();
}
