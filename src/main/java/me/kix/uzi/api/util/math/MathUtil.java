package me.kix.uzi.api.util.math;

import net.minecraft.util.math.AxisAlignedBB;

/**
 * Various math functions used around the client.
 *
 * @author Jackson
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