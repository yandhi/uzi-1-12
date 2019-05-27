package me.kix.uzi.api.util.math.angle;

import me.kix.uzi.api.util.interfaces.MinecraftAccessor;
import me.kix.uzi.api.util.math.vector.Vector3;
import net.minecraft.entity.Entity;

public class AngleUtil implements MinecraftAccessor {

    public static Angle getAngle(Entity entity) {
        Vector3<Double> delta = new Vector3<>();
        delta.setX(entity.posX - mc.player.posX).setY((entity.posY + entity.getEyeHeight()) - (mc.player.posY + entity.getEyeHeight())).setZ(entity.posZ - mc.player.posZ);
        double hypot = Math.hypot(delta.getX(), delta.getZ());
        double calcedYaw = Math.atan2(delta.getZ(), delta.getX());
        double calcedPitch = Math.atan2(delta.getY(), hypot);
        double degrees = 180 / Math.PI;
        return new Angle((float) (calcedYaw * degrees) - 90, (float) -(calcedPitch * degrees)).normalizeAngle();
    }

    public static Angle smoothAngle(Angle source, Angle target, float percent) {
        final Angle angle = new Angle(0, 0);
        return angle
                .setYaw(source.getYaw() - target.getYaw())
                .setPitch(source.getPitch() - target.getPitch())
                .normalizeAngle()
                .setYaw(source.getYaw() - (angle.getYaw() * percent))
                .setPitch(source.getPitch() - (angle.getPitch() * percent))
                .normalizeAngle();
    }

}
