package me.kix.uzi.api.util.math.angle;

import me.kix.uzi.api.util.interfaces.MinecraftAccessor;
import me.kix.uzi.api.util.math.vector.Vector3;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class AngleUtil implements MinecraftAccessor {

    public static Angle getAngle(BlockPos block) {
        Vector3<Double> delta = new Vector3<>();
        delta.setX(block.getX() - mc.player.posX).setY((block.getY()) - (mc.player.posY)).setZ(block.getZ() - mc.player.posZ);
        double hypot = Math.hypot(delta.getX(), delta.getZ());
        double calcedYaw = Math.atan2(delta.getZ(), delta.getX());
        double calcedPitch = Math.atan2(delta.getY(), hypot);
        double degrees = 180 / Math.PI;
        return new Angle((float) (calcedYaw * degrees) - 90, (float) -(calcedPitch * degrees)).normalizeAngle();
    }

    public static Angle getAngle(Entity entity) {
        Vector3<Double> delta = new Vector3<>();
        delta.setX(entity.posX - mc.player.posX).setY((entity.posY + entity.getEyeHeight()) - (mc.player.posY + entity.getEyeHeight())).setZ(entity.posZ - mc.player.posZ);
        double hypot = Math.hypot(delta.getX(), delta.getZ());
        double calcedYaw = Math.atan2(delta.getZ(), delta.getX());
        double calcedPitch = Math.atan2(delta.getY(), hypot);
        double degrees = 180 / Math.PI;
        return new Angle((float) (calcedYaw * degrees) - 90, (float) -(calcedPitch * degrees));
    }

    public static Angle getAngle(Entity entity, double height) {
        Vector3<Double> delta = new Vector3<>();
        delta.setX(entity.posX - mc.player.posX).setY((entity.posY + height) - (mc.player.posY + entity.getEyeHeight())).setZ(entity.posZ - mc.player.posZ);
        double hypot = Math.hypot(delta.getX(), delta.getZ());
        double calcedYaw = Math.atan2(delta.getZ(), delta.getX());
        double calcedPitch = Math.atan2(delta.getY(), hypot);
        double degrees = 180 / Math.PI;
        return new Angle((float) (calcedYaw * degrees) - 90, (float) -(calcedPitch * degrees)).normalizeAngle();
    }

    public static Angle getAngle(TileEntity entity) {
        Vector3<Double> delta = new Vector3<>();
        delta.setX(entity.getPos().getX() - mc.player.posX).setY((entity.getPos().getY() + 0.5) - (mc.player.posY + 0.5)).setZ(entity.getPos().getZ() - mc.player.posZ);
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

    /**
     * The difference between the angles.
     * <p>
     * x - y.
     *
     * @param x The first one (x)
     * @param y The second one (y)
     * @return The difference.
     */
    public static Angle difference(Angle x, Angle y) {
        return new Angle(x.getYaw() - y.getYaw(), x.getPitch() - y.getPitch());
    }

    public static float getAngleDifference(float direction, float rotationYaw) {
        float phi = Math.abs(rotationYaw - direction) % 360;
        float distance = phi > 180 ? 360 - phi : phi;
        return distance;
    }

    public static boolean isEntityInFov(EntityLivingBase entity, double angle) {
        angle *= 0.5;
        double angleDifference = getAngleDifference(Minecraft.getMinecraft().player.rotationYaw, AngleUtil.getAngle(entity).getYaw());
        return (angleDifference > 0.0 && angleDifference < angle)
                || (-angle < angleDifference && angleDifference < 0.0);
    }

    public static float getTrajectoryAngleSolutionLow(final float d3, final float d1, final float velocity) {
        final float g = 0.006f;
        final float sqrt = velocity * velocity * velocity * velocity - g * (g * d3 * d3 + 2.0f * d1 * velocity * velocity);
        return (float) Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(sqrt)) / (g * d3)));
    }

}
