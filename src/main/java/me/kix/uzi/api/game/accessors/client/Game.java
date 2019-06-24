package me.kix.uzi.api.game.accessors.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;

/**
 * The main outline for the game.
 *
 * @author Kix
 * @since April 2018.
 */
public interface Game {

    /**
     * @return The game's timer.
     */
    Timer getTimer();

    /**
     * Sets the session.
     *
     * @param session The new session.
     */
    void setSession(Session session);

    /**
     * Changes the delay between right click presses.
     *
     * @param delay The new delay.
     */
    void setRightClickDelayTimer(int delay);

    /**
     * Allows us to click the mouse through minecraft's functions.
     *
     * @param button The mouse button to click with.
     */
    void clickMouse(int button);

    /**
     * @return The entity currently being rendered.
     */
    Entity getRenderViewEntity();

    /**
     * @return The current FPS.
     */
    int getDebugFPS();

    /**
     * @return The instance of the class that this accessor is modifying.
     */
    Minecraft getInstance();
}
