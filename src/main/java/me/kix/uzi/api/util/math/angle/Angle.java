package me.kix.uzi.api.util.math.angle;

public class Angle {

    private float yaw, pitch;

    public Angle(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public Angle setYaw(float yaw) {
        this.yaw = yaw;
        return this;
    }

    public Angle setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    /**
     * Passed around the Counter-Strike cheating community.
     * Converted to Java by Tojatta.
     *
     * @return Normalized Angle
     */
    public Angle normalizeAngle() {

        this.setYaw(this.getYaw() % 360F);
        this.setPitch(this.getPitch() % 360F);

        while (this.getYaw() <= -180F) {
            this.setYaw(this.getYaw() + 360F);
        }

        while (this.getPitch() <= -180F) {
            this.setPitch(this.getPitch() + 360F);
        }

        while (this.getYaw() > 180F) {
            this.setYaw(this.getYaw() - 360F);
        }

        while (this.getPitch() > 180F) {
            this.setPitch(this.getPitch() - 360F);
        }

        return this;
    }

}
