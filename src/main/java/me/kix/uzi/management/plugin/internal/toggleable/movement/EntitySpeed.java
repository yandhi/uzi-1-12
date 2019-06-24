package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.entity.IEntity;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.MovementInput;

/**
 * Allows us to go faster on entities.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class EntitySpeed extends ToggleablePlugin {

    private final NumberProperty<Double> speed = new NumberProperty<>("Speed", 4.0, 1.0, 10.0);

    public EntitySpeed() {
        super("EntitySpeed", Category.MOVEMENT);
        setDisplay("Entity Speed");
        getProperties().add(speed);
    }

    @Register
    public void onLivingUpdate(EventUpdate.Living event) {
        if (mc.player.getRidingEntity() != null) {
            speed(mc.player.getRidingEntity());

            for (Entity entity : mc.world.loadedEntityList) {
                IEntity iEntity = (IEntity) entity;
                if (iEntity.getRiddenByEntities().contains(mc.player.getRidingEntity())) {
                    for (Entity ridingEntities : iEntity.getRiddenByEntities()) {
                        speed(ridingEntities);
                    }
                }
            }
        }
    }

    /**
     * Speeds up an entity.
     *
     * @param entity The entity being sped up.
     */
    private void speed(Entity entity) {
        MovementInput input = mc.player.movementInput;
        double forward = input.moveForward;
        double strafe = input.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if ((forward == 0) && (strafe == 0)) {
            entity.motionX = 0;
            entity.motionZ = 0;
        } else {
            if (forward != 0) {
                if (strafe > 0) {
                    yaw += (forward > 0 ? -45 : 45);
                } else if (strafe < 0) {
                    yaw += (forward > 0 ? 45 : -45);
                }
                strafe = 0;
                if (forward > 0) {
                    forward = 1;
                } else if (forward < 0) {
                    forward = -1;
                }
            }
            entity.motionX = (forward * speed.getValue() * Math.cos(Math.toRadians(yaw + 90f)) + strafe * speed.getValue() * Math.sin(Math.toRadians(yaw + 90f)));
            entity.motionZ = (forward * speed.getValue() * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed.getValue() * Math.cos(Math.toRadians(yaw + 90.0F)));
            if (entity instanceof EntityMinecart) {
                EntityMinecart entityMinecart = (EntityMinecart) entity;
                entityMinecart.setVelocity((forward * speed.getValue() * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed.getValue() * Math.sin(Math.toRadians(yaw + 90.0F))), entityMinecart.motionY, (forward * speed.getValue() * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed.getValue() * Math.cos(Math.toRadians(yaw + 90.0F))));
            }
        }
    }
}
