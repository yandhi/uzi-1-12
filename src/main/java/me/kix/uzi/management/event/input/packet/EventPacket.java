package me.kix.uzi.management.event.input.packet;

import me.kix.uzi.api.event.cancellable.EventCancellable;
import net.minecraft.network.Packet;

public class EventPacket {

    public static class Send extends EventCancellable {
        private Packet packet;

        public Send(Packet packet) {
            this.packet = packet;
        }

        public Packet getPacket() {
            return packet;
        }

        public void setPacket(Packet packet) {
            this.packet = packet;
        }
    }

    public static class Read extends EventCancellable {
        private final Packet packet;

        public Read(Packet packet) {
            this.packet = packet;
        }

        public Packet getPacket() {
            return packet;
        }
    }

}
