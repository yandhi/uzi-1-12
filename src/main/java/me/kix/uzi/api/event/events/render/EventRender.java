package me.kix.uzi.api.event.events.render;

import me.kix.uzi.api.event.Event;
import me.kix.uzi.api.event.cancellable.EventCancellable;
import net.minecraft.entity.EntityLivingBase;

import javax.vecmath.Vector4f;

public class EventRender {

    public static class WorldToScreen extends Event {
        private final float partialTicks;

        public WorldToScreen(float partialTicks) {
            this.partialTicks = partialTicks;
        }

        public float getPartialTicks() {
            return partialTicks;
        }
    }

    public static class Hand extends Event {
        private final float partialTicks;

        public Hand(float partialTicks) {
            this.partialTicks = partialTicks;
        }

        public float getPartialTicks() {
            return partialTicks;
        }
    }

    public static class TwoDimensional extends Event {
        private final Vector4f box;
        private EntityLivingBase entity;

        public TwoDimensional(Vector4f box, EntityLivingBase entity) {
            this.box = box;
            this.entity = entity;
        }

        public Vector4f getBox() {
            return box;
        }

        public EntityLivingBase getEntity() {
            return entity;
        }
    }

    public static class Hurtcam extends EventCancellable {}

    public static class Pumpkin extends EventCancellable {}

    public static class Potions extends EventCancellable {}

    public static class Bossbar extends EventCancellable {}

    public static class Scoreboard extends EventCancellable {}

    public static class Portal extends EventCancellable {}
}
