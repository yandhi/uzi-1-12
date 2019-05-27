package me.kix.uzi.api.util.math.prediction;

import me.kix.uzi.api.util.math.vector.Vector3;
import net.minecraft.entity.Entity;

/**
 * @author Kix
 * @since 5/26/2018
 */
public class PredicitonUtil {

    private static Vector3<Double> lerp(Vector3<Double> pos, Vector3<Double> prev, float time){
        double x = pos.getX() + ((pos.getX() - prev.getX()) * time);
        double y = pos.getY() + ((pos.getY() - prev.getY()) * time);
        double z = pos.getZ() + ((pos.getZ() - prev.getZ()) * time);

        return new Vector3<Double>(x,y,z);
    }

    public static Vector3<Double> predictPos(Entity entity, float time){
        return lerp(new Vector3<>(entity.posX, entity.posY, entity.posZ), new Vector3<>(entity.prevPosX, entity.prevPosY, entity.prevPosZ), time);
    }


}
