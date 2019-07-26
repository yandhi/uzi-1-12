package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.util.EnumHand;

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

        if (mc.world != null) {
            if (mc.player.getRidingEntity() != null) {
                ridingEntity = mc.player.getRidingEntity();
                mc.player.dismountRidingEntity();
                mc.world.removeEntity(ridingEntity);
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (this.ridingEntity != null) {
            Minecraft.getMinecraft().player.connection.sendPacket(new CPacketUseEntity(this.ridingEntity, EnumHand.MAIN_HAND));
        }
    }

    @Register
    public void onUpdate(EventUpdate.Pre pre) {
        if (ridingEntity != null) {
            ridingEntity.posX = mc.player.posX;
            ridingEntity.posY = mc.player.posY;
            ridingEntity.posZ = mc.player.posZ;

            mc.getConnection().sendPacket(new CPacketVehicleMove(ridingEntity));
        }
    }

    @Register
    public void onSendPacket(EventPacket.Send send) {
        if (send.getPacket() instanceof CPacketPlayer.Position) {
            send.setCancelled(true);
            final CPacketPlayer.Position packet = (CPacketPlayer.Position) send.getPacket();
            mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(packet.getX(mc.player.posX),
                    packet.getY(mc.player.posY), packet.getZ(mc.player.posZ),
                    packet.getYaw(mc.player.rotationYaw), packet.getPitch(mc.player.rotationPitch), packet.isOnGround()));
        }
        if (send.getPacket() instanceof CPacketPlayer && !(send.getPacket() instanceof CPacketPlayer.PositionRotation)) {
            send.setCancelled(true);
        }
        if (send.getPacket() instanceof CPacketUseEntity) {
            final CPacketUseEntity packet = (CPacketUseEntity) send.getPacket();
            if (this.ridingEntity != null) {
                final Entity entity = packet.getEntityFromWorld(mc.world);
                if (entity != null) {
                    this.ridingEntity.posX = entity.posX;
                    this.ridingEntity.posY = entity.posY;
                    this.ridingEntity.posZ = entity.posZ;
                    mc.player.connection.sendPacket(new CPacketVehicleMove(this.ridingEntity));
                }
            }
        }
    }
}
