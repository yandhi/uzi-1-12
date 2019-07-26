package me.kix.uzi.api.event.events.entity;

import me.kix.uzi.api.event.cancellable.EventCancellable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

/**
 * Called when the player dismounts.
 *
 * @author Kix
 * @since 7/16/2019
 */
public class EventEntityDismount extends EventCancellable {

    /**
     * The rider.
     */
    private final EntityLivingBase rider;

    /**
     * The entity being dismounted.
     */
    private final Entity dismountedEntity;

    public EventEntityDismount(EntityLivingBase rider, Entity dismountedEntity) {
        this.rider = rider;
        this.dismountedEntity = dismountedEntity;
    }

    public EntityLivingBase getRider() {
        return rider;
    }

    public Entity getDismountedEntity() {
        return dismountedEntity;
    }
}
