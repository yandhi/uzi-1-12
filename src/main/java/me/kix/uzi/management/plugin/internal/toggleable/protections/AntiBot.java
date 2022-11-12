package me.kix.uzi.management.plugin.internal.toggleable.protections;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;

public class AntiBot extends ToggleablePlugin {

    public AntiBot() {
        super("AntiBot", Category.PROTECTIONS);
        setDisplay("Anti Bot");
        setColor(0xFFA0B8E8);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (isBot(player))
                mc.world.removeEntity(player);
        }
    }

    private boolean isBot(EntityPlayer entity) {
        return entity.getUniqueID().toString().startsWith(entity.getName()) ||
                !StringUtils.stripControlCodes(entity.getGameProfile().getName()).equals(entity.getName()) ||
                entity.getGameProfile().getId() != entity.getUniqueID();
    }

}
