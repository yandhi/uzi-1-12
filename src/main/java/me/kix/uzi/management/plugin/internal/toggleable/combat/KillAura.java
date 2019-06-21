package me.kix.uzi.management.plugin.internal.toggleable.combat;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.math.angle.Angle;
import me.kix.uzi.api.util.math.angle.AngleUtil;
import me.kix.uzi.api.util.math.timing.Timer;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

import java.util.ArrayList;
import java.util.List;

public class KillAura extends ToggleablePlugin {

    private final NumberProperty<Integer> aps = new NumberProperty<>("APS", 15, 1, 20);
    private final NumberProperty<Float> range = new NumberProperty<>("Range", 4f, 3f, 6f);
    private final Property<Boolean> cooldown = new Property<>("Cooldown", true);
    private final Property<Boolean> players = new Property<>("Players", true);
    private final Property<Boolean> animals = new Property<>("Animals", false);
    private final Property<Boolean> monsters = new Property<>("Monsters", false);
    private final Timer timer = new Timer();
    private final List<EntityLivingBase> entities = new ArrayList<>();
    private EntityLivingBase target;

    public KillAura() {
        super("KillAura", Category.COMBAT);
        setDisplay("Kill Aura");
        setColor(-55552);
        getProperties().add(aps);
        getProperties().add(range);
        getProperties().add(cooldown);
        getProperties().add(players);
        getProperties().add(animals);
        getProperties().add(monsters);
    }

    @Register
    public void onPreUpdate(EventUpdate.Pre event) {
        target = getBestTarget();
        if (target != null) {
            Angle angle = AngleUtil.getAngle(target);
            event.getViewAngles()
                    .setYaw(angle.getYaw())
                    .setPitch(angle.getPitch());
        }
    }

    @Register
    public void onPostUpdate(EventUpdate.Post event) {
        if (target != null) {
            if (cooldown.getValue()) {
                if (mc.player.getCooledAttackStrength(0) >= 1 && timer.completed(250)) {
                    attack(target);
                    timer.reset();
                }
            } else {
                if (timer.completed(1000 / aps.getValue())) {
                    attack(target);
                    timer.reset();
                }
            }
        }
    }

    private void attack(EntityLivingBase target) {
        mc.player.swingArm(EnumHand.MAIN_HAND);
        mc.playerController.attackEntity(mc.player, target);
    }

    private EntityLivingBase getBestTarget() {
        entities.clear();
        for (Entity ent : mc.world.loadedEntityList) {
            if (ent instanceof EntityLivingBase) {
                if (isEntityApplicable(ent)) {
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

    private boolean isEntityApplicable(Entity entity) {
        boolean notMe = entity != mc.player;
        boolean withinRange = mc.player.getDistanceToEntity(entity) <= range.getValue();
        boolean existedLongEnough = entity.ticksExisted >= 10;
        boolean alive = entity.isEntityAlive();
        boolean oneOfChosenEntities = entity instanceof EntityPlayer && players.getValue() || entity instanceof EntityAnimal && animals.getValue() || (entity instanceof EntityMob && monsters.getValue());
        return notMe && withinRange && existedLongEnough && alive && oneOfChosenEntities && !Uzi.INSTANCE.getFriendManager().isFriend(entity.getName());
    }

}
