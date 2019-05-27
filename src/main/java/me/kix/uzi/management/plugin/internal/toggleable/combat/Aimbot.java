package me.kix.uzi.management.plugin.internal.toggleable.combat;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.math.angle.Angle;
import me.kix.uzi.api.util.math.angle.AngleUtil;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class Aimbot extends ToggleablePlugin {

    private final NumberProperty<Float> range = new NumberProperty<>("Range", 4f, 3f, 6f);
    private final Property<Boolean> smoothing = new Property<Boolean>("Smoothing", true);
    private final List<EntityPlayer> entities = new ArrayList<>();
    private EntityLivingBase target;

    public Aimbot() {
        super("Aimbot", Category.COMBAT);
        getProperties().add(range);
        getProperties().add(smoothing);
        setColor(0xFFE693AA);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        target = getBestTarget();
        if (target != null) {
            Angle angle = AngleUtil.getAngle(target);
            Angle smoothed = AngleUtil.smoothAngle(new Angle(mc.player.rotationYaw, mc.player.rotationPitch), angle, 0.1f);
            mc.player.rotationYaw = (smoothing.getValue() ? smoothed.getYaw() : angle.getYaw());
            mc.player.rotationPitch = (smoothing.getValue() ? smoothed.getPitch() : angle.getPitch());
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
        entities.sort((player, t1) -> Float.compare(mc.player.getDistanceToEntity(player), mc.player.getDistanceToEntity(t1)));
        return entities.get(0);
    }

    private boolean isEntityApplicable(Entity entity) {
        boolean notMe = entity != mc.player;
        boolean withinRange = mc.player.getDistanceToEntity(entity) <= range.getValue();
        boolean existedLongEnough = entity.ticksExisted >= 10;
        boolean alive = entity.isEntityAlive();
        boolean player = entity instanceof EntityPlayer;
        return notMe && withinRange && existedLongEnough && alive && player && !Uzi.INSTANCE.getFriendManager().isFriend(entity.getName());
    }

}
