package me.kix.uzi.api.game.accessors.packet;

/**
 * The packet that deals with player actions.
 *
 * @author Kix
 * @since May 2018.
 */
public interface PacketPlayer {

    /**
     * The x pos of the player.
     */
    double getX();

    /**
     * The y pos of the player.
     */
    double getY();

    /**
     * The z pos of the player.
     */
    double getZ();

    /**
     * Changes the server-side x position of the player.
     *
     * @param x The new x position.
     */
    void setX(double x);

    /**
     * Changes the server-side y position of the player.
     *
     * @param y The new y position.
     */
    void setY(double y);

    /**
     * Changes the server-side z position of the player.
     *
     * @param z The new x position.
     */
    void setZ(double z);

    /**
     * Changes the server-side on-ground state of the player.
     *
     * @param onGround The new on-ground state of the player.
     */
    void setOnGround(boolean onGround);
}
