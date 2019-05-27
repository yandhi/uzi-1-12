package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.render.EventRender;

/**
 * Hides the portal overlay.
 *
 * @author Jax
 * Created in Apr 2019
 */
public class Portal extends ToggleablePlugin {

    public Portal() {
        super("Portal", Category.RENDER);
        setHidden(true);
    }

    @Register
    public void onRenderPortal(EventRender.Portal portal) {
        portal.setCancelled(true);
    }
}
