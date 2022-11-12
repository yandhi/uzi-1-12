package me.kix.uzi.management.plugin.internal.toggleable.server;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.client.multiplayer.PlayerController;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.block.EventClickBlock;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

/**
 * oHare made this module.
 *
 * originally made for civcraft.
 * 
 * <p>
 * Fixed a little bit by k1x.
 * </p>
 * 
 * @author oHare
 * @since Given to me April 2019
 */
public class CivBreak extends ToggleablePlugin {

	private CPacketPlayerDigging packet;
	private BlockPos pos;
	private boolean sendClick;

	public CivBreak() {
		super("CivBreak", Category.SERVER);
		setColor(0xFF8BBECF);
		setDisplay("Civ Break");
	}

	@Override
	public void onDisable() {
		super.onDisable();
		sendClick = false;
		pos = null;
	}

	@Register
	public void onPacket(EventPacket.Send event) {
		if (event.getPacket() instanceof CPacketPlayerDigging) {
			if (pos != null && ((CPacketPlayerDigging) event.getPacket())
					.getAction() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
				packet = (CPacketPlayerDigging) event.getPacket();
			}
		}
	}

	@Register
	public void onClickBlock(EventClickBlock event) {
		if (!sendClick) {
			pos = event.getPos();
		}
	}

	@Register
	public void onPreUpdate(EventUpdate.Pre event) {
		if (mc.world == null) {
			packet = null;
		}

		if (pos != null) {
			float distance = MathHelper.sqrt(mc.player.getDistanceSq(pos));
			if (distance > 6) {
				if (packet != null) {
					packet = null;
					mc.player.connection.getNetworkManager().sendPacket(new CPacketPlayerDigging(
							CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.UP));
				}
				sendClick = true;
				mc.playerController.onPlayerDamageBlock(new BlockPos(0, 0, 0), EnumFacing.UP);
				sendClick = false;
				return;
			}
		}
	}

	@Register
	public void onPostUpdate(EventUpdate.Post event) {
		if (pos != null) {
			Block block = mc.world.getBlockState(pos).getBlock();
			if (block != Blocks.AIR) {
				float distance = MathHelper.sqrt(mc.player.getDistanceSq(pos));
				if (distance > 6) {
					if (packet != null) {
						packet = null;
						mc.player.connection.getNetworkManager().sendPacket(new CPacketPlayerDigging(
								CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.UP));
					}
					sendClick = true;
					mc.playerController.onPlayerDamageBlock(new BlockPos(0, 0, 0), EnumFacing.UP);
					sendClick = false;
					return;
				}
				((PlayerController) mc.playerController).setBlockHitDelay(0);
				if (pos != null && packet != null && pos.toString().equals(packet.getPosition().toString())) {
					mc.player.connection.getNetworkManager().sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
					mc.player.connection.getNetworkManager().sendPacket(packet);
				} else {
					packet = null;
				}
				if (pos != null && packet == null) {
					sendClick = true;
					mc.player.connection.getNetworkManager().sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
					mc.playerController.onPlayerDamageBlock(pos, EnumFacing.UP);
					mc.player.swingArm(EnumHand.MAIN_HAND);
					sendClick = false;
				}
				if (!(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock)) {
					mc.player.connection.getNetworkManager().sendPacket(new CPacketPlayerTryUseItemOnBlock(pos,
							EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
				}
				if (packet != null && mc.player.getDistanceSq(packet.getPosition()) < 25.0
						&& mc.player.ticksExisted % 3 == 0) {
					for (int i = 0; i < 50; ++i) {
						mc.player.connection.getNetworkManager().sendPacket(packet);
					}
				} else {
					sendClick = false;
				}
			} else {
				pos = null;
			}
		}
	}

}
