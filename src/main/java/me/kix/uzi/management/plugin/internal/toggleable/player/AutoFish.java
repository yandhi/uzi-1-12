package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.math.timing.Timer;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class AutoFish extends ToggleablePlugin {

    private boolean caught;
    private final Timer timer = new Timer();

    public AutoFish() {
        super("AutoFish", Category.PLAYER);
        setDisplay("Auto Fish");
        setColor(0x9F9F9F);
    }

    @Register
    public void onPacket(EventPacket.Read event) {
        if (mc.player != null) {
            if (caught) {
                if (timer.completed(1000)) {
                    caught = false;
                    mc.getConnection().sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                }
            } else if (mc.player.fishEntity != null && event.getPacket() instanceof SPacketSoundEffect) {
                Packet sound = event.getPacket();
                if (mc.player.getHeldItemMainhand().getItem() instanceof ItemFishingRod &&
                        ((SPacketSoundEffect) sound).getSound().getSoundName().equals(new ResourceLocation("entity.bobber.splash"))) {
                    caught = true;
                    timer.reset();
                    mc.getConnection().sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                }
            }
        }
    }

}
