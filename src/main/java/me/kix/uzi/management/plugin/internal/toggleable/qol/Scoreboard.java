package me.kix.uzi.management.plugin.internal.toggleable.qol;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.render.EventRender;

/**
 * Hides the scoreboard.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class Scoreboard extends ToggleablePlugin {

    public Scoreboard() {
        super("Scoreboard", Category.QOL);
        setHidden(true);
    }

    @Register
    public void onRenderScoreboard(EventRender.Scoreboard event){
        event.setCancelled(true);
    }

}
