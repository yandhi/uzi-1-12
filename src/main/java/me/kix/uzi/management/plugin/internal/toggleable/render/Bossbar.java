package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.render.EventRender;

/**
 * Hides the bossbar.
 *
 * @author Jax
 * Created in Apr 2019
 */
public class Bossbar extends ToggleablePlugin {

    public Bossbar() {
        super("Bossbar", Category.RENDER);
        setHidden(true);
    }

    @Register
    public void onRenderBossbar(EventRender.Bossbar bossbar) {
        bossbar.setCancelled(true);
    }

}
