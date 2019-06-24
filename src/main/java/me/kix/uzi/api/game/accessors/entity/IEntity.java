package me.kix.uzi.api.game.accessors.entity;

import net.minecraft.entity.Entity;

import java.util.List;

/**
 * An outline for all entities in the game.
 *
 * @author Kix
 * Created in Apr 2019
 */
public interface IEntity {

    /**
     * @return The ridden entities.
     */
    List<Entity> getRiddenByEntities();
}
