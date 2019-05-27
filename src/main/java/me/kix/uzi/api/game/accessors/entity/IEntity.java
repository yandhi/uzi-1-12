package me.kix.uzi.api.game.accessors.entity;

import net.minecraft.entity.Entity;

import java.util.List;

/**
 * @author Jax
 * Created in Apr 2019
 */
public interface IEntity {

    /**
     * @return The ridden entities.
     */
    List<Entity> getRiddenByEntities();
}
