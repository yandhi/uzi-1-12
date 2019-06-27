package me.kix.uzi.api.event.events.entity;

import me.kix.uzi.api.event.Event;
import net.minecraft.entity.Entity;

/**
 * An attack event that occurs on the post of an attack.
 *
 * <p>
 * Allows us to do dumb things before entities are dead.
 * </p>
 *
 * @author Kix
 * Created in 06 2019.
 */
public class EventPostAttack extends Event {

    /**
     * The entity being attacked.
     */
    private final Entity entity;

    public EventPostAttack(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
