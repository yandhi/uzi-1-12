package me.kix.uzi.management.plugin.internal.toggleable.world;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.render.EventRender;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import me.kix.uzi.api.util.math.MathUtil;
import me.kix.uzi.api.util.math.angle.Angle;
import me.kix.uzi.api.util.math.angle.AngleUtil;
import me.kix.uzi.api.util.math.timing.Timer;
import me.kix.uzi.api.util.render.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Automatically breaks blocks in a radius for the player.
 *
 * <p>
 * I rewrote this bc i want it to actually work for survival.
 * </p>
 *
 * @author yandhi
 * @since idk, rewrote this in july of 2021.
 */
public class Nuker extends ToggleablePlugin {

    /**
     * How far from the player the radius should expand.
     */
    private final NumberProperty<Integer> radius = new NumberProperty<>("Radius", 2, 1, 6, 1);

    /**
     * The time to wait in between block updates.
     */
    private final NumberProperty<Integer> delay = new NumberProperty<>("Delay", 1000, 0, 10000, 100);

    /**
     * The blocks currently in the radius.
     */
    private final Set<BlockPos> blocks = new HashSet<>();

    /**
     * The queue for breaking blocks.
     */
    private final Queue<BlockPos> breakQueue = new ConcurrentLinkedQueue<>();

    /**
     * The block currently being broken.
     */
    private BlockPos currentBlock;

    /**
     * Allows us to add delay to the plugin.
     */
    private final Timer timer = new Timer();

    public Nuker() {
        super("Nuker", Category.WORLD);
        getProperties().add(radius);
        getProperties().add(delay);
        setColor(0x65E69A);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        breakQueue.clear();
    }

    @Register
    public void preUpdate(EventUpdate.Pre event) {
        if (isRadiusClean()) {
            blocks.clear();
        }

        /* Populate the radius with blocks. */
        for (int x = -radius.getValue(); x < radius.getValue(); x++) {
            for (int y = radius.getValue(); y > -radius.getValue(); y--) {
                for (int z = -radius.getValue(); z < radius.getValue(); z++) {
                    BlockPos blockPos = new BlockPos(mc.player.posX + x, mc.player.posY + y, mc.player.posZ + z);
                    blocks.add(blockPos);
                }
            }
        }

        /* Make sure it's an actual block. */
        blocks.stream()
                .filter(block -> mc.world.getBlockState(block).getBlock() != Blocks.AIR)
                .forEach(breakQueue::add);

        /* Make ourselves a current block if we are in need of one. */
        if ((currentBlock == null || mc.world.getBlockState(currentBlock).getBlock() == Blocks.AIR) && timer.completed(delay.getValue())) {
            BlockPos polled = breakQueue.poll();
            BlockPos delta = polled.subtract(mc.player.getPosition());

            if (delta.getX() >= radius.getValue() || delta.getY() >= radius.getValue() || delta.getZ() >= radius.getValue()) {
                polled = null;
                breakQueue.clear();
            }

            currentBlock = polled;
            timer.reset();
        }

        /* Look at the block that we are currently working on. */
        if (currentBlock != null && mc.world.getBlockState(currentBlock).getBlock() != Blocks.AIR) {
            Angle angle = AngleUtil.getAngle(currentBlock);
            event.setViewAngles(angle);
        }
    }

    @Register
    public void postUpdate(EventUpdate.Post post) {
        if (currentBlock != null && mc.world.getBlockState(currentBlock).getBlock() != Blocks.AIR) {
            mc.playerController.onPlayerDamageBlock(currentBlock, EnumFacing.getFacingFromVector(currentBlock.getX(), currentBlock.getY(), currentBlock.getZ()));
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }


    @Register
    public void onRenderHand(EventRender.Hand hand) {
        for (BlockPos block : blocks) {
            if (mc.world.getBlockState(block).getBlock() != Blocks.AIR) {
                double x = block.getX() - mc.getRenderManager().viewerPosX;
                double y = block.getY() - mc.getRenderManager().viewerPosY;
                double z = block.getZ() - mc.getRenderManager().viewerPosZ;
                AxisAlignedBB boundingBox = new AxisAlignedBB(0d, 0d, 0d, 1d, 1d, 1d).offset(x, y, z);
                RenderUtil.bb(boundingBox, 2, block == currentBlock ? new Color(255, 0, 0, 70) : new Color(119, 255, 0, 70));
            }
        }
    }

    /**
     * @return Whether or not the radius is completely clean.
     */
    private boolean isRadiusClean() {
        boolean air = true;

        for (BlockPos blockPos : blocks) {
            air = mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR;
        }

        return air;
    }

}
