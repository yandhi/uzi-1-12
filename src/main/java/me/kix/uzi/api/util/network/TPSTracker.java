package me.kix.uzi.api.util.network;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import net.minecraft.network.play.server.SPacketTimeUpdate;

/**
 * Allows us to track server TPS.
 *
 * <p>
 * This is used as a lazily instantiated instance.
 * </p>
 *
 * <p>
 * Thanks brennan for helping a bit with this.
 * </p>
 *
 * @author Kix
 * Created in 06 2019.
 */
public final class TPSTracker {

    /**
     * The singleton instance of the tracker.
     */
    private static TPSTracker tracker;

    /**
     * The last known system update time.
     */
    private long lastSystemTime = System.currentTimeMillis();

    /**
     * An array that ensures that our TPS count will be accurate based on 10 given tps'.
     */
    private float[] tpsCounts = new float[10];

    /**
     * The current server TPS.
     *
     * <p>
     * By default, this is set to the maximum tps.
     * </p>
     */
    private long tps = 20;

    @Register
    public void onReadPacket(EventPacket.Read read) {
        if (read.getPacket() instanceof SPacketTimeUpdate) {
            long currentTime = System.currentTimeMillis();

            if (lastSystemTime == -1) {
                lastSystemTime = currentTime;
                return;
            }
            long timeDiff = currentTime - lastSystemTime;
            float tickTime = timeDiff / 20;
            if (tickTime == 0) {
                tickTime = 50;
            }
            float tps = 1000 / tickTime;
            if (tps > 20.0f) {
                tps = 20.0f;
            }
            System.arraycopy(tpsCounts, 0, tpsCounts, 1, tpsCounts.length - 1);
            tpsCounts[0] = tps;

            double total = 0.0;
            for (float f : tpsCounts) {
                total += f;
            }
            total /= tpsCounts.length;


            if (total > 20.0) {
                total = 20.0;
            }

            this.tps = Math.round(total);
            lastSystemTime = currentTime;
        }
    }

    /**
     * Lazily instantiated tracker instance.
     *
     * @return An instance of the tracker.
     */
    public static TPSTracker getTracker() {
        if (tracker == null) {
            tracker = new TPSTracker();
        }
        return tracker;
    }

    public long getTps() {
        return tps;
    }
}
