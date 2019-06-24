package me.kix.uzi.api.game.accessors.entity;

/**
 * An implementation of {@link net.minecraft.entity.Entity} that is living.
 *
 * @author Kix
 * @since May 2018.
 */
public interface LivingEntity {

    /**
     * @return Whether or not the entity is jumping.
     */
    boolean getIsJumping();
}
