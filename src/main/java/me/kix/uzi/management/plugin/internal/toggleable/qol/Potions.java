package me.kix.uzi.management.plugin.internal.toggleable.qol;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.render.EventRender;

/**
 * Hides the minecraft potion effect renders.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class Potions extends ToggleablePlugin {

    public Potions() {
        super("Potions", Category.QOL);
        setHidden(true);
    }

    @Register
    public void onRenderPotions(EventRender.Potions potions) {
        potions.setCancelled(true);
    }

}
