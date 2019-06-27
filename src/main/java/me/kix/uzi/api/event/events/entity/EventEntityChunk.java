package me.kix.uzi.api.event.events.entity;

import me.kix.uzi.api.event.Event;
import net.minecraft.entity.Entity;

public class EventEntityChunk {

    public static class Enter extends Event {

        /**
         * The entity entering the chunk.
         */
        private final Entity entity;

        public Enter(Entity entity) {
            this.entity = entity;
        }

        /**
         * @return the entity
         */
        public Entity getEntity() {
            return entity;
        }
    }

    public static class Leave extends Event {
        
        /**
         * The entity leaving the chunk.
         */
        private final Entity entity;

        public Leave(Entity entity) {
            this.entity = entity;
        }

        /**
         * @return the entity
         */
        public Entity getEntity() {
            return entity;
        }
    }

}