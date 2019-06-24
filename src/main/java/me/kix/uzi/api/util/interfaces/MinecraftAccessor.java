package me.kix.uzi.api.util.interfaces;

import net.minecraft.client.Minecraft;

/**
 * Allows a class to gain access to the game.
 *
 * <p>
 * This is legacy uzi code that the whole client relies on.
 * Eventually, this will be changed.
 * ~ Kix (June 2019)
 * </p>
 *
 * <p>
 * TODO: Convert this into a guice dependency and remove reliance on this file.
 * </p>
 *
 * @author Kix
 * @since April 2018.
 */
public interface MinecraftAccessor {

    Minecraft mc = Minecraft.getMinecraft();
}
