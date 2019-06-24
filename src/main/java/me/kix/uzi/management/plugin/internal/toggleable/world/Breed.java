package me.kix.uzi.management.plugin.internal.toggleable.world;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.math.timing.Timer;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemShears;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;

/**
 * @author Kix
 * @since 8/26/18
 */
public class Breed extends ToggleablePlugin {

	private final Timer timer = new Timer();

	public Breed() {
		super("Breed", Category.WORLD);
		setColor(0xFFD172B8);
	}

	@Register
	public void onUpdate(EventUpdate.Pre event) {
		EntityPlayerSP player = mc.player;
		if (player.getHeldItemMainhand().getItem() != Items.AIR && (player.getHeldItemMainhand().getItem() instanceof ItemFood || player.getHeldItemMainhand().getItem() == Items.WHEAT)) {
			for (Entity entity : mc.world.loadedEntityList) {
				if (entity instanceof EntityAnimal && player.getDistanceToEntity(entity) <= 6) {
						player.connection.sendPacket(new CPacketUseEntity(entity, EnumHand.MAIN_HAND));
				}
			}
		}
	}

}
