package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.TextComponentString;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The sign hack.
 *
 * @author Kix
 * @since 7/12/2019
 */
public class Signs extends ToggleablePlugin {

    public Signs() {
        super("Signs", Category.MISCELLANEOUS);
    }

    @Register
    public void onSendPacket(EventPacket.Send send) {
        if (send.getPacket() instanceof CPacketUpdateSign) {
            CPacketUpdateSign packet = (CPacketUpdateSign) send.getPacket();
            IntStream gen = random.ints(128, 1112063).map(i -> (i < 55296) ? i : (i + 2048));
            String line = gen.limit(1536L).mapToObj(i -> String.valueOf((char)i)).collect((Collectors.joining()));
            for (int j = 0; j < 4; ++j) {
                packet.getLines()[j] = line.substring(j * 384, (j + 1) * 384);
            }
        }
    }

    @Register
    public void onUpdate(EventUpdate.Pre preUpdate) {
        for (TileEntity e : mc.world.loadedTileEntityList) {
            if (e instanceof TileEntitySign) {
                TileEntitySign sign = (TileEntitySign) e;

                for (int i = 0; i <= 3; i++) {
                    sign.signText[i] = new TextComponentString("");
                }
            }
        }
    }

}
