package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.BlockPos;

/**
 * Lags nearby players.
 *
 * <p>
 * Thanks Riga.
 * </p>
 *
 * @author Kix
 * Created in 06 2019.
 */
public class SoundLag extends ToggleablePlugin {

    /**
     * The amount of swap packets to send.
     */
    private final NumberProperty<Integer> packets = new NumberProperty<>("Packets", 20, 5, 10000);

    public SoundLag() {
        super("SoundLag", Category.MISCELLANEOUS);
        setDisplay("Sound Lag");
        getProperties().add(packets);
    }

    @Register
    public void onPreUpdate(EventUpdate.Pre event) {
        for (int i = 0; i <= this.packets.getValue(); i++) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
        }
    }
}
