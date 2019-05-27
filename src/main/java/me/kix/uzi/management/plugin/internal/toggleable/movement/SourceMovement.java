package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.entity.IPlayerSP;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventMotion;

public class SourceMovement extends ToggleablePlugin {

    public SourceMovement() {
        super("SourceMovement", Category.MOVEMENT);
        setDisplay("Source Movement");
    }

    @Register
    public void onMotion(EventMotion motion){
        if((mc.player.hurtTime == 0) && ((mc.player.moveForward != 0.0F) || (mc.player.moveStrafing != 0.0F))){
            IPlayerSP player = (IPlayerSP) mc.player;
            player.setSpeed(player.getSpeed());
         }
    }

}