package me.kix.uzi.api.util.entity;

import me.kix.uzi.api.util.interfaces.MinecraftAccessor;
import me.kix.uzi.api.util.math.angle.Angle;
import me.kix.uzi.api.util.math.angle.AngleUtil;
import net.minecraft.entity.EntityLivingBase;

/**
 * Originally from huzuni, but I've updated to work with uzi's api.
 *
 * @author jackson/halalaboos
 * @since idk, but now 2022
 */
public class EntityTracker implements MinecraftAccessor {

    /**
     * The entity that we are currently tracking.
     */
    private EntityLivingBase entity;

    /**
     * Our current rotations plus the rate at which we are turning.
     */
    private float currentYaw, currentPitch, rotationRate = 5F;

    /**
     * Whether we have reached the entity.
     */
    private boolean hasReached = false;

    /**
     * Updates the fake rotations and calculates whether the rotations have fully rotated to meet the given entity.
     */
    public void updateRotations() {
        Angle angle = AngleUtil.getAngle(entity);
        float[] rotationCaps = EntityUtil.getEntityCaps(entity, 6.5f);
        float yawDifference = AngleUtil.getAngleDifference(currentYaw, angle.getYaw()), pitchDifference = angle.getPitch() - currentPitch;
        float absoluteYawDifference = Math.abs(yawDifference), absolutePitchDifference = Math.abs(pitchDifference);
        this.hasReached = absoluteYawDifference < rotationCaps[0] && absoluteYawDifference > -rotationCaps[0] && absolutePitchDifference < rotationCaps[1] && absolutePitchDifference > -rotationCaps[1];

        if (this.hasReached) {
            float realYawDifference = AngleUtil.getAngleDifference(mc.player.rotationYaw % 360F, angle.getYaw()), realPitchDifference = angle.getPitch() - (mc.player.rotationPitch % 360F);
            if (realYawDifference < rotationCaps[0] && realYawDifference > -rotationCaps[0])
                currentYaw = mc.player.rotationYaw % 360F;
            if (realPitchDifference < rotationCaps[1] && realPitchDifference > -rotationCaps[1])
                currentPitch = mc.player.rotationPitch % 360F;
        } else {
            if (yawDifference > rotationCaps[0] || yawDifference < -rotationCaps[0]) {
                float yawAdjustment = clamp(yawDifference, rotationRate);
                if (yawAdjustment < 0)
                    currentYaw += yawAdjustment;
                else if (yawAdjustment > 0)
                    currentYaw += yawAdjustment;
            }
            if (pitchDifference > rotationCaps[1] || pitchDifference < -rotationCaps[1]) {
                float pitchAdjustment = clamp(pitchDifference, rotationRate);
                if (pitchAdjustment < 0)
                    currentPitch += pitchAdjustment;
                else if (pitchAdjustment > 0)
                    currentPitch += pitchAdjustment;
            }
        }
    }

    /**
     * Clamps the given input by the max value.
     *
     * @param input The given input.
     * @param max   The given max.
     */
    private float clamp(float input, float max) {
        if (input > max)
            input = max;
        if (input < -max)
            input = -max;
        return input;
    }

    /**
     * Resets the fake rotation.
     */
    public void reset() {
        hasReached = false;
        if (mc.player != null) {
            currentYaw = mc.player.rotationYaw % 360;
            currentPitch = mc.player.rotationPitch % 360;
        }
    }

    public void setEntity(EntityLivingBase entity) {
        if (entity != this.entity) {
            this.entity = entity;
            reset();
        }
    }

    public boolean hasReached() {
        return hasReached;
    }

    public float getYaw() {
        return currentYaw;
    }

    public float getPitch() {
        return currentPitch;
    }

    public float getRotationRate() {
        return rotationRate;
    }

    public void setRotationRate(float rotationRate) {
        this.rotationRate = rotationRate;
    }

    public boolean hasEntity() {
        return entity != null;
    }

}