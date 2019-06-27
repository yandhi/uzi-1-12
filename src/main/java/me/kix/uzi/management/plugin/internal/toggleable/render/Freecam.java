package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.block.EventBoundingBox;
import me.kix.uzi.api.event.events.entity.EventMotion;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.play.client.*;

import java.util.Objects;

public class Freecam extends ToggleablePlugin {

    private double x;
    private double y;
    private double z;

    public Freecam() {
        super("Freecam", Category.RENDER);
        setColor(0xFFCFECFF);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        mc.player.setVelocity(0.0, 0.0, 0.0);
        mc.player.jumpMovementFactor = 1;
        if (mc.currentScreen == null) {
            if (GameSettings.isKeyDown(mc.gameSettings.keyBindJump)) {
                final EntityPlayerSP thePlayer = mc.player;
                thePlayer.motionY += 1;
            }
            if (GameSettings.isKeyDown(mc.gameSettings.keyBindSneak)) {
                final EntityPlayerSP thePlayer2 = mc.player;
                thePlayer2.motionY -= 1;
            }
        }
        mc.player.noClip = true;
        mc.player.renderArmPitch = 5000.0f;
    }

    @Register
    public void onMotion(EventMotion event) {
        if (!GameSettings.isKeyDown(mc.gameSettings.keyBindSneak) && !GameSettings.isKeyDown(mc.gameSettings.keyBindJump)) {
            event.setY(1 * 2.0 * -(mc.player.rotationPitch / 180.0f) * (int) mc.player.movementInput.moveForward);
        }
    }

    @Register
    public void onPacketSend(EventPacket.Send event) {
        if (event.getPacket() instanceof CPacketPlayer || event.getPacket() instanceof CPacketPlayerDigging || event.getPacket() instanceof CPacketPlayerTryUseItem || event.getPacket() instanceof CPacketEntityAction || event.getPacket() instanceof CPacketUseEntity || event.getPacket() instanceof CPacketAnimation)
            event.setCancelled(true);
    }

    @Register
    public void onPacketRead(EventPacket.Read event) {
        if (event.getPacket() instanceof CPacketPlayer || event.getPacket() instanceof CPacketPlayerDigging || event.getPacket() instanceof CPacketPlayerTryUseItem || event.getPacket() instanceof CPacketEntityAction || event.getPacket() instanceof CPacketUseEntity || event.getPacket() instanceof CPacketAnimation)
            event.setCancelled(true);
    }

    @Register
    public void onBoundingBox(EventBoundingBox event) {
        event.setAabb(null);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (Objects.nonNull(mc.world)) {
            this.x = mc.player.posX;
            this.y = mc.player.posY;
            this.z = mc.player.posZ;
            final EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP(mc.world, mc.player.getGameProfile());
            entityOtherPlayerMP.inventory = mc.player.inventory;
            entityOtherPlayerMP.inventoryContainer = mc.player.inventoryContainer;
            entityOtherPlayerMP.setPositionAndRotation(this.x, mc.player.getEntityBoundingBox().minY, this.z, mc.player.rotationYaw, mc.player.rotationPitch);
            entityOtherPlayerMP.rotationYawHead = mc.player.rotationYawHead;
            entityOtherPlayerMP.setSneaking(mc.player.isSneaking());
            mc.world.addEntityToWorld(-6969, entityOtherPlayerMP);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (Objects.nonNull(mc.world)) {
            mc.player.jumpMovementFactor = 0.02f;
            mc.player.setPosition(this.x, this.y, this.z);
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.01, mc.player.posZ, mc.player.onGround));
            mc.player.noClip = false;
            mc.world.removeEntityFromWorld(-6969);
            mc.player.motionY = 0.0;
        }
        mc.renderGlobal.loadRenderers();
    }

}
