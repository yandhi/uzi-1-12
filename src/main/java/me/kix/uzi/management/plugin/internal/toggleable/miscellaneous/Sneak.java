package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.network.play.client.CPacketEntityAction;

public class Sneak extends ToggleablePlugin {

    public Sneak() {
        super("Sneak", Category.MISCELLANEOUS);
        setColor(-16668155);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        boolean sneaking = mc.player.isSneaking();
        boolean moving = mc.player.movementInput.moveForward != 0;
        boolean strafing = mc.player.movementInput.moveStrafe != 0;
        moving = moving || strafing;
        if (!moving || sneaking) {
            mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        } else {
            mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }

    @Register
    public void onUpdate(EventUpdate.Post event) {
        mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
    }

}
