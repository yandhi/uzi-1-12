package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.impl.packet.AccessorSPacketEntityVelocity;
import me.kix.uzi.api.game.impl.packet.AccessorSPacketExplosion;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import me.kix.uzi.api.property.properties.NumberProperty;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class AntiVelocity extends ToggleablePlugin {

    /**
     * The velocity modifier.
     */
    private NumberProperty<Double> modifier = new NumberProperty<>("Modifier", 0.0, 0.0, 0.99, 0.01);

    public AntiVelocity() {
        super("AntiVelocity", Category.MOVEMENT);
        setColor(-5061693);
        setDisplay("Anti Velocity");

    }

    @Override
    public void initPlugin() {
        super.initPlugin();
        getProperties().add(modifier);
    }

    @Register
    public void onPacketRead(EventPacket.Read event) {

        if (event.getPacket() instanceof SPacketEntityVelocity) {
            if (modifier.getValue() == 0) {
                event.setCancelled(true);
            } else {
                AccessorSPacketEntityVelocity accessorSPacketEntityVelocity = (AccessorSPacketEntityVelocity) event.getPacket();
                accessorSPacketEntityVelocity.setMotionX((int) (accessorSPacketEntityVelocity.getMotionX() * modifier.getValue()));
                accessorSPacketEntityVelocity.setMotionY((int) (accessorSPacketEntityVelocity.getMotionY() * modifier.getValue()));
                accessorSPacketEntityVelocity.setMotionZ((int) (accessorSPacketEntityVelocity.getMotionZ() * modifier.getValue()));
            }
        }

        if (event.getPacket() instanceof SPacketExplosion) {
            if (modifier.getValue() == 0) {
                event.setCancelled(true);
            } else {
                AccessorSPacketExplosion accessorSPacketExplosion = (AccessorSPacketExplosion) event.getPacket();
                accessorSPacketExplosion.setMotionX((float) (accessorSPacketExplosion.getMotionX() * modifier.getValue()));
                accessorSPacketExplosion.setMotionY((float) (accessorSPacketExplosion.getMotionY() * modifier.getValue()));
                accessorSPacketExplosion.setMotionZ((float) (accessorSPacketExplosion.getMotionZ() * modifier.getValue()));
            }
        }
    }
}
