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
    void setTimerSpeed(int speed);

    /**
     * Allows us to modify the game's tick length.
     *
     * @param tickLength The new tick length of the timer.
     */
    void setTickLength(float tickLength);

}
