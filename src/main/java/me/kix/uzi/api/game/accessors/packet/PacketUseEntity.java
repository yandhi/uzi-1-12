package me.kix.uzi.api.game.accessors.packet;

/**
 * The packet for modifying / interacting with entities.
 *
 * @author Kix
 * @since 5/25/2018
 */
public interface PacketUseEntity {

    /**
     * @return The id of the entity currently being interacted with.
     */
    int getEntityId();
}
