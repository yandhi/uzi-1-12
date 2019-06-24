package me.kix.uzi.api.game.impl.entity;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.game.accessors.entity.Player;
import me.kix.uzi.api.util.math.MathUtil;
import me.kix.uzi.management.event.entity.EventMotion;
import me.kix.uzi.management.event.entity.EventUpdate;
import me.kix.uzi.management.event.input.chat.EventSendOffChatMessage;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends MixinEntityLivingBase implements Player {

    @Shadow
    @Final
    public NetHandlerPlayClient connection;
    @Shadow
    private double lastReportedPosX;
    @Shadow
    private double lastReportedPosY;
    @Shadow
    private double lastReportedPosZ;
    @Shadow
    private float lastReportedYaw;
    @Shadow
    private float lastReportedPitch;
    @Shadow
    private boolean serverSprintState;
    @Shadow
    private boolean serverSneakState;
    @Shadow
    private boolean prevOnGround;
    @Shadow
    private int positionUpdateTicks;
    @Shadow
    protected Minecraft mc;
    @Shadow
    private boolean autoJumpEnabled;


    @Shadow
    public abstract boolean isSneaking();

    @Shadow
    protected abstract boolean isCurrentViewEntity();

    @Override
    public void move(MoverType type, double x, double y, double z) {
        EventMotion event = new EventMotion(x, y, z);
        Uzi.INSTANCE.getEventManager().dispatch(event);
        super.move(type, event.getX(), event.getY(), event.getZ());
    }

    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void onUpdate(CallbackInfo ci) {
        Uzi.INSTANCE.getEventManager().dispatch(new EventUpdate.Living());
    }

    /**
     * @author Kix
     * @reason Overwrites the onUpdateWalkingPlayer function so we can have our EventUpdate here.
     */
    @Overwrite
    public void onUpdateWalkingPlayer() {
        EntityPlayerSP _this = (EntityPlayerSP) (Object) this;
        EventUpdate.Pre preEvent = new EventUpdate.Pre(getRotations(), onGround, posY, lastReportedPosY);
        Uzi.INSTANCE.getEventManager().dispatch(preEvent);

        if (preEvent.isCancelled()) {
            Uzi.INSTANCE.getEventManager().dispatch(new EventUpdate.Post());
        }

        boolean clientSprintState = this.isSprinting();
        if (clientSprintState != this.serverSprintState) {
            this.connection.sendPacket(new CPacketEntityAction(_this, clientSprintState ? CPacketEntityAction.Action.START_SPRINTING : CPacketEntityAction.Action.STOP_SPRINTING));
            this.serverSprintState = clientSprintState;
        }

        boolean clientSneakState = this.isSneaking();
        if (clientSneakState != this.serverSneakState) {
            this.connection.sendPacket(new CPacketEntityAction(_this, clientSneakState ? CPacketEntityAction.Action.START_SNEAKING : CPacketEntityAction.Action.STOP_SNEAKING));
            this.serverSneakState = clientSneakState;
        }

        if (this.isCurrentViewEntity()) {
            double d0 = posX - lastReportedPosX;
            double d1 = preEvent.getPosY() - lastReportedPosY;
            double d2 = posZ - lastReportedPosZ;
            double d3 = preEvent.getViewAngles().getYaw() - lastReportedYaw;
            double d4 = preEvent.getViewAngles().getPitch() - lastReportedPitch;
            boolean position = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || ++this.positionUpdateTicks >= 20;
            boolean rotation = d3 != 0.0D || d4 != 0.0D;
            if (this.isRiding()) {
                this.connection.sendPacket(new CPacketPlayer.PositionRotation(this.motionX, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
                position = false;
            } else if (position && rotation) {
                this.connection.sendPacket(new CPacketPlayer.PositionRotation(posX, preEvent.getPosY(), posZ, preEvent.getViewAngles().getYaw(), preEvent.getViewAngles().getPitch(), preEvent.isOnGround()));
            } else if (position) {
                this.connection.sendPacket(new CPacketPlayer.Position(posX, preEvent.getPosY(), posZ, preEvent.isOnGround()));
            } else if (rotation) {
                this.connection.sendPacket(new CPacketPlayer.Rotation(preEvent.getViewAngles().getYaw(), preEvent.getViewAngles().getPitch(), preEvent.isOnGround()));
            } else if (this.prevOnGround != preEvent.isOnGround()) {
                this.connection.sendPacket(new CPacketPlayer(preEvent.isOnGround()));
            }

            if (position) {
                this.lastReportedPosX = posX;
                this.lastReportedPosY = preEvent.getPosY();
                this.lastReportedPosZ = posZ;
                this.positionUpdateTicks = 0;
            }

            if (rotation) {
                this.lastReportedYaw = preEvent.getViewAngles().getYaw();
                this.lastReportedPitch = preEvent.getViewAngles().getPitch();
            }

            this.prevOnGround = preEvent.isOnGround();
            this.autoJumpEnabled = this.mc.gameSettings.autoJump;
        }
        Uzi.INSTANCE.getEventManager().dispatch(new EventUpdate.Post());
    }

    /**
     * Overwriting this in order to register an event here for commands.
     *
     * @param message The message needing to be sent.
     * @author Kix
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

    public float getDirection() {
		float yaw = mc.player.rotationYaw;
		if (mc.player.moveForward < 0) {
			yaw += 180;
		}
		float forward = 1;
		if (mc.player.moveForward < 0) {
			forward = -0.5F;
		} else if (mc.player.moveForward > 0) {
			forward = 0.5F;
		}
		if (mc.player.moveStrafing > 0) {
			yaw -= 90 * forward;
		}
		if (mc.player.moveStrafing < 0) {
			yaw += 90 * forward;
		}
		yaw *= 0.017453292F;
		return yaw;
	}

    @Override
    public void setSpeed(double speed) {
        this.motionX = -MathHelper.sin(this.getDirection()) * speed;
		this.motionZ = MathHelper.cos(this.getDirection()) * speed;
    }

    @Override
    public double getSpeed() {
        return Math.sqrt(MathUtil.square(this.motionX) + MathUtil.square(this.motionZ));
    }

}
