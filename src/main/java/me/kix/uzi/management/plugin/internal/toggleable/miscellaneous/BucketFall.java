package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

/**
 * An MLG water-bucket hack that works every time!
 *
 * @author Jax
 * Created in Apr 2019
 */
public class BucketFall extends ToggleablePlugin {

    public BucketFall() {
        super("BucketFall", Category.MISCELLANEOUS);
        setDisplay("Bucket Fall");
    }

    @Register
    public void onUpdate(EventUpdate.Pre pre) {
        if (!mc.player.onGround && mc.player.fallDistance <= 3 && mc.player.fallDistance >= 1 && mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.WATER_BUCKET) {
            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, -90, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(mc.player.getPosition().down(2), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0, 0, 0));
            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, mc.player.rotationPitch, mc.player.onGround));
            toggle();
        }
    }
}
