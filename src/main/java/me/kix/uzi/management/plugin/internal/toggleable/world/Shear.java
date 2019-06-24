package me.kix.uzi.management.plugin.internal.toggleable.world;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.math.timing.Timer;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.item.ItemShears;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;

/**
 * @author Kix
 * @since 8/26/18
 */
public class Shear extends ToggleablePlugin {

	private final Timer timer = new Timer();

	public Shear() {
		super("Shear", Category.WORLD);
		setColor(0xFFCAA322);
	}

	@Register
	public void onUpdate(EventUpdate.Pre event) {
		EntityPlayerSP player = mc.player;
		if (player.getHeldItemMainhand().getItem() != Items.AIR && player.getHeldItemMainhand().getItem() instanceof ItemShears) {
			for (Entity entity : mc.world.loadedEntityList) {
				if (entity instanceof EntitySheep && player.getDistanceToEntity(entity) <= 6) {
					if (!((EntitySheep) entity).getSheared()) {
							player.connection.sendPacket(new CPacketUseEntity(entity, EnumHand.MAIN_HAND));
					}
				}
			}
		}
	}
}
