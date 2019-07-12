package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.misc.EventTick;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketVehicleMove;

/**
 * Allows the player to begin god mode.
 *
 * <p>
 * The player does become invisible to others.
 * </p>
 *
 * @author Kix
 * @since 7/10/2019
 */
public class Vanish extends ToggleablePlugin {

    /**
     * The last riding entity.
     */
    private Entity ridingEntity;

    public Vanish() {
        super("Vanish", Category.PLAYER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (mc.player.getRidingEntity() != null) {
            ridingEntity = mc.player.getRidingEntity();
            mc.player.dismountRidingEntity();
            mc.world.removeEntity(ridingEntity);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ridingEntity = null;
    }

    @Register
    public void onTick(EventTick tick) {
        if (ridingEntity != null) {
            ridingEntity.posX = mc.player.posX;
            ridingEntity.posY = mc.player.posY;
            ridingEntity.posZ = mc.player.posZ;

            mc.getConnection().sendPacket(new CPacketVehicleMove(ridingEntity));
        }
    }

}
