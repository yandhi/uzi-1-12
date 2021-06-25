package me.kix.uzi.api.game.impl.entity;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.game.accessors.entity.Player;
import me.kix.uzi.api.util.math.MathUtil;
import me.kix.uzi.api.event.events.block.EventPushOutOfBlocks;
import me.kix.uzi.api.event.events.entity.EventMotion;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import me.kix.uzi.api.event.events.input.chat.EventSendOffChatMessage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.MoverType;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends MixinEntityPlayer implements Player {

    @Shadow
    @Final
    public NetHandlerPlayClient connection;
    @Shadow
    protected Minecraft mc;

    @Shadow
    public abstract boolean isSneaking();

    /**
     * Pre update event.
     */
    private EventUpdate.Pre updateWalkingPlayer;

    @Override
    public void move(MoverType type, double x, double y, double z) {
        EventMotion event = new EventMotion(x, y, z);
        Uzi.INSTANCE.getEventManager().dispatch(event);
        super.move(type, event.getX(), event.getY(), event.getZ());
    }

    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    private void pushOutOfBlocks(double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        EventPushOutOfBlocks pushOutOfBlocks = new EventPushOutOfBlocks();
        Uzi.INSTANCE.getEventManager().dispatch(pushOutOfBlocks);

        if (pushOutOfBlocks.isCancelled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void onUpdate(CallbackInfo ci) {
        Uzi.INSTANCE.getEventManager().dispatch(new EventUpdate.Living());
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"))
    private void onUpdateWalkingPlayerHead(CallbackInfo ci) {
        updateWalkingPlayer = new EventUpdate.Pre(getRotations(), onGround, posY, prevPosY);
        Uzi.INSTANCE.getEventManager().dispatch(updateWalkingPlayer);
    }

    @Redirect(method = "onUpdateWalkingPlayer", at = @At(value = "FIELD", target = "Lnet/minecraft/util/math/AxisAlignedBB;minY:D"))
    private double onUpdateWalkingPlayerMinY(AxisAlignedBB boundingBox) {
        return updateWalkingPlayer.getPosY();
    }

    @Redirect(method = "onUpdateWalkingPlayer", at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;onGround:Z"))
    private boolean onUpdateWalkingPlayerOnGround(EntityPlayerSP player) {
        return updateWalkingPlayer.isOnGround();
    }

    @Redirect(method = "onUpdateWalkingPlayer", at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;rotationYaw:F"))
    private float onUpdateWalkingPlayerRotationYaw(EntityPlayerSP player) {
        return updateWalkingPlayer.getViewAngles().getYaw();
    }

    @Redirect(method = "onUpdateWalkingPlayer", at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;rotationPitch:F"))
    private float onUpdateWalkingPlayerRotationPitch(EntityPlayerSP player) {
        return updateWalkingPlayer.getViewAngles().getPitch();
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("RETURN"))
    private void onUpdateWalkingPlayerReturn(CallbackInfo ci) {
        Uzi.INSTANCE.getEventManager().dispatch(new EventUpdate.Post());
    }

    /**
     * Overwriting this in order to register an event here for commands.
     *
     * @param message The message needing to be sent.
     * @author Kix
     * @reason To hook into chat functionality.
     */
    @Overwrite
    public void sendChatMessage(String message) {
        EventSendOffChatMessage event = new EventSendOffChatMessage(message);
        Uzi.INSTANCE.getEventManager().dispatch(event);
        if (event.isCancelled()) return;
        this.connection.sendPacket(new CPacketChatMessage(event.getMessage()));
    }

    @Override
    public boolean isInLiquid() {
        for (int x = MathHelper.floor(Minecraft.getMinecraft().player.getEntityBoundingBox().minX); x < MathHelper.floor(Minecraft.getMinecraft().player.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor(Minecraft.getMinecraft().player.getEntityBoundingBox().minZ); z < MathHelper.floor(Minecraft.getMinecraft().player.getEntityBoundingBox().maxZ) + 1; ++z) {
                final BlockPos pos = new BlockPos(x, (int) Minecraft.getMinecraft().player.getEntityBoundingBox().minY, z);
                final Block block = Minecraft.getMinecraft().world.getBlockState(pos).getBlock();
                if (!(block instanceof BlockAir)) {
                    return block instanceof BlockLiquid;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isOnLiquid() {
        if (mc.player == null)
            return false;
        boolean onLiquid = false;
        int y = (int) mc.player.getEntityBoundingBox().offset(0.0D, -0.01D, 0.0D).minY;
        for (int x = MathHelper.floor(mc.player.getEntityBoundingBox().minX); x < MathHelper
                .floor(mc.player.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor(mc.player.getEntityBoundingBox().minZ); z < MathHelper
                    .floor(mc.player.getEntityBoundingBox().maxZ) + 1; z++) {
                Block block = mc.world.getBlockState(new BlockPos(x, y, z))
                        .getBlock();
                if ((block != null) && (!(block instanceof BlockAir))) {
                    if (!(block instanceof BlockLiquid))
                        return false;
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }

    @Override
    public boolean isMoving() {
        return mc.player.movementInput.forwardKeyDown || mc.player.movementInput.backKeyDown || mc.player.movementInput.leftKeyDown || mc.player.movementInput.rightKeyDown;
    }


    @Override
    public void setSpeed(double speed) {
        this.motionX = -MathHelper.sin(MathUtil.getDirection(rotationYaw)) * speed;
        this.motionZ = MathHelper.cos(MathUtil.getDirection(rotationYaw)) * speed;
    }

    @Override
    public double getSpeed() {
        return Math.sqrt(MathUtil.square(this.motionX) + MathUtil.square(this.motionZ));
    }

}
