package me.kix.uzi.api.game.accessors.client.math;

/**
 * The outline for Minecraft's timing system.
 *
 * @author Kix
 * @since August 2018.
 */
public interface GameTimer {

    /**
     * Allows us to change the game's timer speed.
     *
     * @param speed The new speed of the timer.
     */
    void setTimerSpeed(float speed);
}
