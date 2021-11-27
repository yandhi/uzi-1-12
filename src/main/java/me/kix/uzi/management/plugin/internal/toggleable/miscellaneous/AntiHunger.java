package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import net.minecraft.network.play.client.CPacketPlayerDigging;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AntiHunger extends ToggleablePlugin {

    private Queue<CPacketPlayerDigging> blockBreaks;
    private boolean lastOnGround, lastPacketOnGround;

    public AntiHunger() {
        super("AntiHunger", Category.MISCELLANEOUS);
        setDisplay("Anti Hunger");
        setColor(0xE6E24B);
        blockBreaks = new ConcurrentLinkedQueue<>();
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        try {
            if (!blockBreaks.isEmpty()) {
                event.setOnGround(true);
                while (!blockBreaks.isEmpty()) {
                    mc.getConnection().getNetworkManager().sendPacket(Objects.requireNonNull(blockBreaks.poll()));
                }
                return;
            }
            if (!event.isOnGround() && lastOnGround && event.getPosY() > event.getLastY()) {
                event.setOnGround(true);
                return;
            }
            if (event.isOnGround() && lastOnGround && !mc.player.isInWater() && !mc.player.isOnLadder()) {
                event.setOnGround(false);
            }
        } finally {
            lastOnGround = mc.player.onGround;
            lastPacketOnGround = event.isOnGround();
        }
    }

    @Register
    public void onPacket(EventPacket.Send event) {
        if (mc.player.onGround == lastPacketOnGround) {
            return;
        }
        if (event.getPacket() != null) {
            if (event.getPacket() instanceof CPacketPlayerDigging) {
                CPacketPlayerDigging playerDigging = ((CPacketPlayerDigging) event.getPacket());
                if (playerDigging.getAction() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                    blockBreaks.add(playerDigging);
                    event.setCancelled(true);
                }
            }
        }
    }

}
