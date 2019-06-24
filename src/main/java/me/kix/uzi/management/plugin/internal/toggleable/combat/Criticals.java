package me.kix.uzi.management.plugin.internal.toggleable.combat;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.entity.LivingEntity;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.input.packet.EventPacket;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

public class Criticals extends ToggleablePlugin {

    /**
     * The offsets for critical amounts.
     *
     * <p>
     * Standard 4 packet criticals.
     * </p>
     */
    private final double[] offsets = new double[]{0.0625, 0.0, 1.1E-5, 0.0};

    public Criticals() {
        super("Criticals", Category.COMBAT);
        setColor(-1664953);
    }

    @Register
    public void onPacketSent(EventPacket.Send event) {
        if (event.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity pack = (CPacketUseEntity) event.getPacket();
            if (pack.getAction() == CPacketUseEntity.Action.ATTACK) {
                if (!((LivingEntity) mc.player).getIsJumping()) {
                    for (double offset : offsets) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + offset, mc.player.posZ, false));
                    }
                }
            }
        }
    }

}
