package me.kix.uzi.management.plugin.internal.toggleable.world;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.math.timing.Timer;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class Nuker extends ToggleablePlugin {

    private final NumberProperty<Integer> radius = new NumberProperty<>("Radius", 2, 1, 6);
    private final Timer timer;

    public Nuker() {
        super("Nuker", Category.WORLD);
        getProperties().add(radius);
        setColor(0x65E69A);
        timer = new Timer();
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        for (int x = -radius.getValue(); x < radius.getValue(); x++) {
            for (int y = radius.getValue(); y > -radius.getValue(); y--) {
                for (int z = -radius.getValue(); z < radius.getValue(); z++) {
                    BlockPos blockPos = new BlockPos(mc.player.posX + x, mc.player.posY + y, mc.player.posZ + z);
                    Block block = mc.world.getBlockState(blockPos).getBlock();
                    if (block != Blocks.AIR)
                        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                    if (block == Blocks.AIR)
                        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                }
            }
        }
    }

}
