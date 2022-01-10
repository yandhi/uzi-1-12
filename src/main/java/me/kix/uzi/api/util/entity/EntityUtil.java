package me.kix.uzi.api.util.entity;

import me.kix.uzi.api.util.interfaces.MinecraftAccessor;
import net.minecraft.entity.EntityLivingBase;

/**
 * Various methods regarding entities.
 *
 * @author jackson
 * @since 1/3/2022
 */
public class EntityUtil implements MinecraftAccessor {

    /**
     * @return Maximum/minimum rotation leniency allowed to still be considered 'inside' of a given entity.
     * @author halalaboos.
     */
    public static float[] getEntityCaps(EntityLivingBase entity, float distance) {
        float distanceRatio = distance / mc.player.getDistanceToEntity(entity); /* I honestly do not remember my logic behind this and I don't want to bring out a notebook and figure out why this works, but it seems to work */
        float entitySize = 5F; /* magic number */
        return new float[]{distanceRatio * entity.width * entitySize, distanceRatio * entity.height * entitySize};
    }

}
