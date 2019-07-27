package me.kix.uzi.api.event.events.input.packet;

import me.kix.uzi.api.event.cancellable.EventCancellable;
import net.minecraft.network.Packet;

public class EventPacket extends EventCancellable {

    /**
     * The packet for the event.
     */
    private Packet packet;

    public EventPacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public static class Send extends EventPacket {
        public Send(Packet packet) {
            super(packet);
        }
    }

    public static class Read extends EventPacket {
        public Read(Packet packet) {
            super(packet);
        }
    }
}
