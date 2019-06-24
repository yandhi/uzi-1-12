package me.kix.uzi.api.game.accessors.renderer;

/**
 * The main rendering system for the client.
 *
 * @author Kix
 * @since April 2018.
 */
public interface GameRenderManager {

    /**
     * @return The game's x rendering position.
     */
    double getRenderPosX();

    /**
     * @return The game's y rendering position.
     */
    double getRenderPosY();

    /**
     * @return The game's z rendering position.
     */
    double getRenderPosZ();
}
