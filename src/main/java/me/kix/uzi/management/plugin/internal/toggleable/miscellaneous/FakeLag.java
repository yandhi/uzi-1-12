package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.math.timing.Timer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketTabComplete;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Allows the user to act as though they are lagging on a server.
 *
 * <p>
 * This works by delaying the amount of keep alive packets sent to the server.
 * </p>
 *
 * <p>
 * This might somehow work to cause the player to bypass a garbage anti-cheat.
 * I believe faithful used to be able to be cheated on utilizing one of these to disable anti-cheat checks.
 * </p>
 *
 * <p>
 * This only works in game so the player can actually connect to servers.
 * </p>
 *
 * @author Kix
 * @since 7/26/2019
 */
public class FakeLag extends ToggleablePlugin {

    /**
     * The delay between choked packets being sent.
     */
    private final NumberProperty<Integer> throttle = new NumberProperty<>("Throttle", 100, 0, 10000, 100);

    /**
     * The packets currently being choked.
     */
    private final Queue<Packet<?>> chokedPackets = new ConcurrentLinkedQueue<>();

    /**
     * The timing utility for the module.
     */
    private final Timer timer = new Timer();

    /**
     * The packet currently being choked.
     */
    private Packet<?> currentPacket;

    public FakeLag() {
        super("FakeLag", Category.MISCELLANEOUS);
        setDisplay("Fake Lag");
        getProperties().add(throttle);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        chokedPackets.clear();
    }

    @Register
    public void onUpdatePre(EventUpdate.Pre pre) {
        if (timer.completed(throttle.getValue()) && chokedPackets.peek() != null) {
            currentPacket = chokedPackets.poll();
            mc.player.connection.sendPacket(currentPacket);
            timer.reset();
        }
    }

    @Register
    public void onPacketSend(EventPacket.Send send) {
        if (mc.player != null && send.getPacket() != currentPacket) {
            if (send.getPacket() instanceof CPacketKeepAlive || send.getPacket() instanceof CPacketChatMessage || send.getPacket() instanceof CPacketTabComplete) {
                send.setCancelled(true);
                chokedPackets.add(send.getPacket());
            }
        }
    }
}
