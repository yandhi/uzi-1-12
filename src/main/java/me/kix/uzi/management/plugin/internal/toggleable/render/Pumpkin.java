package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.render.EventRender;

/**
 * Hides the pumpkin overlay.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class Pumpkin extends ToggleablePlugin {

    public Pumpkin() {
        super("Pumpkin", Category.RENDER);
        setHidden(true);
    }

    @Register
    public void onRenderPumpkin(EventRender.Pumpkin pumpkin) {
        pumpkin.setCancelled(true);
    }

}
