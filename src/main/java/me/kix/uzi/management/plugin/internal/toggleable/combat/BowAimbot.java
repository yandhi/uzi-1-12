package me.kix.uzi.management.plugin.internal.toggleable.combat;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.math.angle.AngleUtil;
import me.kix.uzi.api.util.math.prediction.PredicitonUtil;
import me.kix.uzi.api.util.math.vector.Vector3;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kix
 * @since 5/26/2018
 */
public class BowAimbot extends ToggleablePlugin {

    /**
     * The field of view.
     */
    private final NumberProperty<Float> fov = new NumberProperty<>("FOV", 360f, 0f, 360f, 1f);

    private final List<EntityLivingBase> entities = new ArrayList<>();
    private EntityLivingBase target;

    public BowAimbot() {
        super("BowAimbot", Category.COMBAT);
        setDisplay("Bow Aimbot");
        setColor(0xFFEBFFA3);
        getProperties().add(fov);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        if (mc.player.getItemInUseCount() == 0)
            return;
        if (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.AIR || !(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBow))
            return;
        int use = mc.player.getItemInUseCount();
        float progress = use / mc.player.getItemInUseMaxCount();
        progress = (progress * progress + progress * 2.0f) / 3.0f;
        progress = MathHelper.clamp(progress, 0, 1);
        double v = progress * 3.0f;
        double g = 0.05f;
        target = getClosest();
        if (target == null) return;
        float pitch = (float) -Math.toDegrees(getLaunchAngle(target, v, g));
        if (Double.isNaN(pitch)) return;
        Vector3<Double> pos = PredicitonUtil.predictPos(target, 10);
        double difX = pos.getX() - mc.player.posX, difZ = pos.getZ() - mc.player.posZ;
        float yaw = (float) (Math.atan2(difZ, difX) * 180 / Math.PI) - 90;
        mc.player.rotationYaw = yaw;
        mc.player.rotationPitch = pitch;
    }

    private EntityLivingBase getClosest() {
        entities.clear();
        for (Entity ent : mc.world.loadedEntityList) {
            if (ent instanceof EntityPlayer) {
                if (ent.isEntityAlive() && !ent.isInvisible() && ent != mc.player && mc.player.canEntityBeSeen(ent) && AngleUtil.isEntityInFov((EntityLivingBase) ent, fov.getValue())) {
                    entities.add((EntityLivingBase) ent);
                }
            }
        }
        if (entities.isEmpty()) {
            return null;
        }
        entities.sort((player, t1) -> Float.compare(mc.player.getDistanceToEntity(player), mc.player.getDistanceToEntity(t1)));
        return entities.get(0);
    }

    private float getLaunchAngle(EntityLivingBase targetEntity, double v, double g) {
        double yDif = ((targetEntity.posY + (targetEntity.getEyeHeight() / 2))
                - (mc.player.posY + mc.player.getEyeHeight()));
        double xDif = (targetEntity.posX - mc.player.posX);
        double zDif = (targetEntity.posZ - mc.player.posZ);
        double xCoord = Math.sqrt((xDif * xDif) + (zDif * zDif));
        return theta(v, g, xCoord, yDif);
    }

    private float theta(double v, double g, double x, double y) {
        double yv = 2 * y * (v * v);
        double gx = g * (x * x);
        double g2 = g * (gx + yv);
        double insqrt = (v * v * v * v) - g2;
        double sqrt = Math.sqrt(insqrt);
        double numerator = (v * v) + sqrt;
        double numerator2 = (v * v) - sqrt;
        double atan1 = Math.atan2(numerator, g * x);
        double atan2 = Math.atan2(numerator2, g * x);
        return (float) Math.min(atan1, atan2);
    }
}
