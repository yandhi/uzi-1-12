package me.kix.uzi.management.plugin.internal.toggleable.qol;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.render.EventRender;

/**
 * Hides the game's hurtcam.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class Hurtcam extends ToggleablePlugin {

    public Hurtcam() {
        super("Hurtcam", Category.QOL);
        setHidden(true);
    }

    @Register
    public void onRenderHurtcam(EventRender.Hurtcam hurtcam) {
        hurtcam.setCancelled(true);
    }

}
