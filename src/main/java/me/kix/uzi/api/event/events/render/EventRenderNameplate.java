package me.kix.uzi.api.event.events.render;

import me.kix.uzi.api.event.cancellable.EventCancellable;
import net.minecraft.entity.Entity;

public class EventRenderNameplate extends EventCancellable {

    /**
     * The entity who's nameplate is being rendered.
     */
    private final Entity entity;

    public EventRenderNameplate(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
