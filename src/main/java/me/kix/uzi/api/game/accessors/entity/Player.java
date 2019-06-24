package me.kix.uzi.api.game.accessors.entity;

/**
 * An outline for all players in the game.
 *
 * @author Kix
 * @since April 2018.
 */
public interface Player {

    /**
     * @return Whether or not the player is in liquid.
     */
    boolean isInLiquid();

    /**
     * @return Whether or not the player is on top of liquid.
     */
    boolean isOnLiquid();

    /**
     * @return Whether or not the player is moving.
     */
    boolean isMoving();

    /**
     * Changes the movement speed of the player.
     *
     * @param speed The new movement speed of the player.
     */
    void setSpeed(double speed);

    /**
     * @return The current movement speed of the player.
     */
    double getSpeed();
}
