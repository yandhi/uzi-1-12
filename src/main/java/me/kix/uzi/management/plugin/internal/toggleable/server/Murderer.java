package me.kix.uzi.management.plugin.internal.toggleable.server;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.logging.Logger;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;

public class Murderer extends ToggleablePlugin {

    private EntityPlayer murderer;

    public Murderer() {
        super("Murderer", Category.RENDER);
        setColor(0xFFEAFF91);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        murderer = getMurderer();
        if (murderer != null) {
            Logger.printMessage(murderer.getName() + " is the murderer!");
            murderer = null;
            toggle();
        }
    }

    private EntityPlayer getMurderer() {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player.getHeldItemMainhand().getItem() instanceof ItemSword) {
                return player;
            }
        }
        return null;
    }

}
