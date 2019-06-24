package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.render.EventRender;

/**
 * Hides the scoreboard.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class Scoreboard extends ToggleablePlugin {

    public Scoreboard() {
        super("Scoreboard", Category.RENDER);
        setHidden(true);
    }

    @Register
    public void onRenderScoreboard(EventRender.Scoreboard event){
        event.setCancelled(true);
    }

}
