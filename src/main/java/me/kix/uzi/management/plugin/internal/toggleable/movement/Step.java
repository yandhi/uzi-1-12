package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.entity.LivingEntity;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventStep;
import net.minecraft.network.play.client.CPacketPlayer;

import java.util.Arrays;

/**
 * Valkyrie's step modified to become instant :)
 */
public class Step extends ToggleablePlugin {

	/**
	 * Valk found these offsets;
	 */
	private static final double[][] stepOffsets = new double[][]{
			{
					0.42,
					0.753
			},
			{
					0.42,
					0.75,
					1,
					1.16,
					1.23,
					1.2
			}
	};

	private double[] offsets;

	public Step() {
		super("Step", Category.MOVEMENT);
		setColor(0xFFA9FFA7);
	}

	@Register
	public void onPreStep(EventStep.Pre event) {
		float stepHeight = 1f;
		if (((LivingEntity) mc.player).getIsJumping() || mc.player.isInWater() || mc.player.isInLava() || !mc.player.isCollidedHorizontally)
			return;
		if (mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(mc.player.motionX, 1.6, mc.player.motionZ)).isEmpty())
			stepHeight = 1.5f;
		if (mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(mc.player.motionX, 1.1, mc.player.motionZ)).isEmpty())
			stepHeight = 1f;

		if ((stepHeight == 1f && mc.world.getCollisionBoxes(mc.player,
				mc.player.getEntityBoundingBox().offset(mc.player.motionX, 0.6, mc.player.motionZ)).isEmpty())
				|| !mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(
				mc.player.motionX, stepHeight + 0.01, mc.player.motionZ)).isEmpty())
			return;
		event.setHeight(stepHeight);
		offsets = stepOffsets[Math.round(stepHeight * 2) - 2];

	}

	@Register
	public void onPostStep(EventStep.Post event) {
		if (((LivingEntity) mc.player).getIsJumping() || mc.player.isInWater() || mc.player.isInLava() || !mc.player.isCollidedHorizontally)
			return;
		if (offsets != null) {
			Arrays.stream(offsets)
					.forEach(offset -> mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + offset, mc.player.posZ, false)));
		}
	}

}
