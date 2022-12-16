package me.kix.uzi.management.plugin.internal.toggleable.combat;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.math.angle.Angle;
import me.kix.uzi.api.util.math.angle.AngleUtil;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Aims at nearby entities.
 *
 * @author jackson
 * @since idk (rewrote in 2022).
 */
public class Aimbot extends ToggleablePlugin {

    /**
     * the fov for the entities.
     */
    private final NumberProperty<Float> fov = new NumberProperty<>("FOV", 100f, 1f, 360f, 1f);

    private final NumberProperty<Float> range = new NumberProperty<>("Range", 4f, 3f, 6f, .1f);
    private final Property<Boolean> smoothing = new Property<Boolean>("Smoothing", true);
    private final List<EntityPlayer> entities = new ArrayList<>();
    private EntityLivingBase target;

    public Aimbot() {
        super("Aimbot", Category.COMBAT);
        setColor(0xFFE693AA);
    }

    @Override
    public void initPlugin() {
        super.initPlugin();
        getProperties().add(fov);
        getProperties().add(range);
        getProperties().add(smoothing);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        target = getBestTarget();
        Angle myViewAngles = new Angle(mc.player.rotationYaw, mc.player.rotationPitch);
        if (target != null) {
            Angle angle = AngleUtil.getAngle(target);

            if (smoothing.getValue()) {
                double randomBone = Math.min(1 + (Math.random() * 1.5), 1.5);
                Angle difference = AngleUtil.difference(AngleUtil.getAngle(target, randomBone), myViewAngles);

                if (difference.getYaw() > 180) {
                    difference.setYaw(difference.getYaw() - 360);
                }

                if (difference.getYaw() < -180) {
                    difference.setYaw(difference.getYaw() + 360);
                }

                double smoothFactor = 5 + (Math.random() * 10);

                mc.player.rotationYaw += difference.getYaw() / smoothFactor;
                mc.player.rotationPitch += difference.getPitch() / smoothFactor;

            } else {
                mc.player.rotationYaw = angle.getYaw();
                mc.player.rotationPitch = angle.getPitch();
            }
        }
    }

    private EntityLivingBase getBestTarget() {
        entities.clear();
        for (Entity ent : mc.world.loadedEntityList) {
            if (isEntityApplicable(ent)) {
                entities.add((EntityPlayer) ent);
            }
        }
        if (entities.isEmpty()) {
            return null;
        }
        entities.sort((player, t1) -> Float.compare(mc.player.getDistance(player), mc.player.getDistance(t1)));
        return entities.get(0);
    }

    private boolean isEntityApplicable(Entity entity) {
        boolean notMe = entity != mc.player;
        boolean withinRange = mc.player.getDistance(entity) <= range.getValue();
        boolean existedLongEnough = entity.ticksExisted >= 10;
        boolean alive = entity.isEntityAlive();
        boolean player = entity instanceof EntityPlayer;
        return notMe && withinRange && existedLongEnough && alive && player && !Uzi.INSTANCE.getFriendManager().isFriend(entity.getName()) && AngleUtil.isEntityInFov((EntityPlayer) entity, fov.getValue());
    }

}
