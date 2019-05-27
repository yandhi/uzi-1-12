package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.render.EventRender;

/**
 * Hides the game's hurtcam.
 *
 * @author Jax
 * Created in Apr 2019
 */
public class Hurtcam extends ToggleablePlugin {

    public Hurtcam() {
        super("Hurtcam", Category.RENDER);
        setHidden(true);
    }

    @Register
    public void onRenderHurtcam(EventRender.Hurtcam hurtcam) {
        hurtcam.setCancelled(true);
    }

}
