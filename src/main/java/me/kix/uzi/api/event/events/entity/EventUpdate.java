package me.kix.uzi.api.event.events.entity;

import me.kix.uzi.api.event.Event;
import me.kix.uzi.api.event.cancellable.EventCancellable;
import me.kix.uzi.api.util.math.angle.Angle;

public class EventUpdate {

    /**
     * Only on the normal update sequence.
     * Essentially a tick.
     */
    public static class Living extends Event {

    }

    /**
     * On the beginning of walking player.
     */
    public static class Pre extends EventCancellable {
        private final Angle viewAngles;
        private boolean onGround;
        public double posY, lastY;

        public Pre(Angle viewAngles, boolean onGround, double posY, double lastY) {
            this.viewAngles = viewAngles;
            this.onGround = onGround;
            this.posY = posY;
            this.lastY = lastY;
        }

        public Angle getViewAngles() {
            return viewAngles;
        }

        public void setViewAngles(Angle angle) {
            this.viewAngles.setYaw(angle.getYaw());
            this.viewAngles.setPitch(angle.getPitch());
        }

        public boolean isOnGround() {
            return onGround;
        }

        public void setOnGround(boolean onGround) {
            this.onGround = onGround;
        }

        public double getPosY() {
            return posY;
        }

        public void setPosY(double posY) {
            this.posY = posY;
        }

        public double getLastY() {
            return lastY;
        }
    }

    /**
     * End of walking player.
     */
    public static class Post extends Event {
        private final Angle viewAngles;

        public Post(Angle viewAngles) {
            this.viewAngles = viewAngles;
        }

        public Angle getViewAngles() {
            return viewAngles;
        }
    }
}
