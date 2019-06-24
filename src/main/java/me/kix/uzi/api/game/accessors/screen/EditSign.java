package me.kix.uzi.api.game.accessors.screen;

import net.minecraft.tileentity.TileEntitySign;

/**
 * The gui screen for a sign.
 *
 * @author Kix
 * Created in Apr 2019
 */
public interface EditSign {

    /**
     * @return The instance of the sign being edited.
     */
    TileEntitySign getTileSign();
}
