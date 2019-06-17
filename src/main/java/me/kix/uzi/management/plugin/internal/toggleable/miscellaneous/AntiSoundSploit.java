package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.input.packet.EventPacket;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;

/**
 * Prevents the sound exploit from occurring.
 *
 * <p>
 * Thanks Riga.
 * </p>
 *
 * @author Kix
 * Created in 06 2019.
 */
public class AntiSoundSploit extends ToggleablePlugin {

    public AntiSoundSploit() {
        super("AntiSoundSploit", Category.MISCELLANEOUS);
        setDisplay("Anti Soundsploit");
    }

    @Register
    public void onReadPackets(EventPacket.Read read) {
        if (read.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect) read.getPacket();
            if (packet.getCategory() == SoundCategory.PLAYERS && packet.getSound() == SoundEvents.ITEM_ARMOR_EQUIP_GENERIC) {
                read.setCancelled(true);
            }
        }
    }

}
