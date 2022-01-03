package me.kix.uzi.api.util.math;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

/**
 * Various math functions used around the client.
 *
 * @author Kix
 * @since April 2019
 */
public class MathUtil {

    /**
     * Squares the provided value.
     *
     * @param value The value to be squared.
     */
    public static double square(double value) {
        return value * value;
    }

    /**
     * Converts radians to degrees.
     *
     * @param rad The starting radians.
     * @return The degrees value based on the radians.
     * @author Rigamortis
     */
    public static double radToDeg(double rad) {
        return rad * (float) (180.0f / Math.PI);
    }

    /**
     * Converts degrees to radians.
     *
     * @param deg The starting degrees.
     * @return The radians value based on the degree provided.
     * @author Rigamortis
     */
    public static double degToRad(double deg) {
        return deg * (float) (Math.PI / 180.0f);
    }

    /**
     * Gets the vector of entity direction.
     *
     * @param yaw The starting yaw.
     * @return The vector of the direction.
     * @author Rigamortis
     */
    public static Vec3d direction(float yaw) {
        return new Vec3d(Math.cos(degToRad(yaw + 90f)), 0, Math.sin(degToRad(yaw + 90f)));
    }

    /**
     * Produces a number where the likelihood of getting a higher choice is greater.
     *
     * @return The random number but laying upon a bell curve.
     */
    public static double inverseBellCurveRandom() {
        return 1 - bellCurveRandom();
    }

    /**
     * Produces a number where the likelihood of getting a lower choice is greater.
     *
     * @return The random number but laying upon a bell curve.
     */
    public static double bellCurveRandom() {
        return Math.pow(2 * Math.random() - 1, 2);
    }

    /**
     * Gets the direction based on yaw.
     *
     * @param yaw The yaw to gain the direction from.
     * @return The direction of the entity.
     * @author Vlakreeh
     * @since circa 2015
     */
    public static float getDirection(float yaw) {
        if (Minecraft.getMinecraft().player.moveForward < 0) {
            yaw += 180;
        }
        float forward = 1;
        if (Minecraft.getMinecraft().player.moveForward < 0) {
            forward = -0.5F;
        } else if (Minecraft.getMinecraft().player.moveForward > 0) {
            forward = 0.5F;
        }
        if (Minecraft.getMinecraft().player.moveStrafing > 0) {
            yaw -= 90 * forward;
        }
        if (Minecraft.getMinecraft().player.moveStrafing < 0) {
            yaw += 90 * forward;
        }
        yaw *= 0.017453292F;
        return yaw;
    }

    /**
     * Creates a bounding box from the given bounds.
     *
     * @param minX The minimum x value.
     * @param minY The minimum y value.
     * @param minZ The minimum z value.
     * @param maxX The maximum x value.
     * @param maxY The maximum y value.
     * @param maxZ The maximum z value.
     * @return The newly created {@link AxisAlignedBB}.
     */
    public static AxisAlignedBB fromBoundariesAxisAlignedBB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        double x = Math.min(minX, maxX);
        double y = Math.min(minY, maxY);
        double z = Math.min(minZ, maxZ);
        double x1 = Math.max(minX, maxX);
        double y1 = Math.max(minY, maxY);
        double z1 = Math.max(minZ, maxZ);

        return new AxisAlignedBB(x, y, z, x1, y1, z1);
    }

}