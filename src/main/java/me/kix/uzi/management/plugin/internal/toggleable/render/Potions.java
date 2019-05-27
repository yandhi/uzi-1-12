package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.render.EventRender;

/**
 * Hides the minecraft potion effect renders.
 *
 * @author Jax
 * Created in Apr 2019
 */
public class Potions extends ToggleablePlugin {

    public Potions() {
        super("Potions", Category.RENDER);
        setHidden(true);
    }

    @Register
    public void onRenderPotions(EventRender.Potions potions) {
        potions.setCancelled(true);
    }

}
