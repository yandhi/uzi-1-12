package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.input.packet.EventPacket;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class AntiVelocity extends ToggleablePlugin {

    public AntiVelocity() {
        super("AntiVelocity", Category.MOVEMENT);
        setColor(-5061693);
        setDisplay("Anti Velocity");
    }

    @Register
    public void onPacketRead(EventPacket.Read event) {
        if (event.getPacket() instanceof SPacketEntityVelocity || event.getPacket() instanceof SPacketExplosion) {
            event.setCancelled(true);
        }
    }

}
