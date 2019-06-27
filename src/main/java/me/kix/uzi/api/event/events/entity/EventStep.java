package me.kix.uzi.api.event.events.entity;

import me.kix.uzi.api.event.Event;
import net.minecraft.entity.Entity;

public class EventStep {

    public static class Pre extends Event {
        private Entity entity;
        private float height;

        public Pre(Entity entity) {
            this.entity = entity;
            this.height = entity.stepHeight;
        }

        public Entity getEntity() {
            return entity;
        }

        public void setEntity(Entity entity) {
            this.entity = entity;
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
        }

    }

    public static class Post extends Event {
        private Entity entity;
        private float height;

        public Post(Entity entity) {
            this.entity = entity;
            this.height = entity.stepHeight;
        }

        public Entity getEntity() {
            return entity;
        }

        public void setEntity(Entity entity) {
            this.entity = entity;
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
        }
    }

}
