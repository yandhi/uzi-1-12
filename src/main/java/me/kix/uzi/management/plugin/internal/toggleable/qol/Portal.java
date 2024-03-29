package me.kix.uzi.management.plugin.internal.toggleable.qol;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.render.EventRender;

/**
 * Hides the portal overlay.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class Portal extends ToggleablePlugin {

    public Portal() {
        super("Portal", Category.QOL);
        setHidden(true);
    }

    @Register
    public void onRenderPortal(EventRender.Portal portal) {
        portal.setCancelled(true);
    }
}
