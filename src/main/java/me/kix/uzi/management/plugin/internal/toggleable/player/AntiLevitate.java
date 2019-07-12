package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import net.minecraft.potion.Potion;

import java.util.Objects;

/**
 * Prevents the player from levitating.
 *
 * @author Kix
 * @since 6/30/2019
 */
public class AntiLevitate extends ToggleablePlugin {

    public AntiLevitate() {
        super("AntiLevitate", Category.PLAYER);
        setDisplay("Anti Levitate");
    }

    @Register
    public void onUpdate(EventUpdate.Pre preUpdate) {
        if (mc.player.getActivePotionEffect(Objects.requireNonNull(Potion.getPotionById(25))) != null) {
            mc.player.removeActivePotionEffect(Potion.getPotionById(25));
        }
    }
}
