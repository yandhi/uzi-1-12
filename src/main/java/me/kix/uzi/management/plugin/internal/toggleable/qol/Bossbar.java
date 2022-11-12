package me.kix.uzi.management.plugin.internal.toggleable.qol;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.render.EventRender;

/**
 * Hides the bossbar.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class Bossbar extends ToggleablePlugin {

    public Bossbar() {
        super("Bossbar", Category.QOL);
        setHidden(true);
    }

    @Register
    public void onRenderBossbar(EventRender.Bossbar bossbar) {
        bossbar.setCancelled(true);
    }

}
