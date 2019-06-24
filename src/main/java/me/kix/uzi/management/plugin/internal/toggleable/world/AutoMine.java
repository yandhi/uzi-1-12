package me.kix.uzi.management.plugin.internal.toggleable.world;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.management.event.entity.EventUpdate;
import me.kix.uzi.management.event.input.mouse.EventMousePressed;
import me.kix.uzi.management.event.render.EventRender;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import java.awt.*;

/**
 * Automatically mines the block that is selected if it is not air.
 *
 * <p>
 * This is most useful for SkyBlock.
 * </p>
 *
 * @author Kix
 * Created in Apr 2019
 */
public class AutoMine extends ToggleablePlugin {

    /**
     * The player's block selection.
     */
    private BlockPos selection;

    /**
     * The side that the selection was selected at.
     */
    private EnumFacing selectionSide;

    public AutoMine() {
        super("AutoMine", Category.WORLD);
        setDisplay("Auto Mine");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.selection = null;
        this.selectionSide = null;
    }

    @Register
    public void onMouseClick(EventMousePressed mousePressed) {
        if (mousePressed.getMouseButton() == 1) {
            if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
                selection = mc.objectMouseOver.getBlockPos();
                selectionSide = mc.objectMouseOver.sideHit;
            }
        }
    }

    @Register
    public void onRenderHand(EventRender.Hand hand) {
        if (selection != null) {
            double x = selection.getX() - mc.getRenderManager().viewerPosX;
            double y = selection.getY() - mc.getRenderManager().viewerPosY;
            double z = selection.getZ() - mc.getRenderManager().viewerPosZ;
            AxisAlignedBB boundingBox = new AxisAlignedBB(0d, 0d, 0d, 1d, 1d, 1d).offset(x, y, z);
            RenderUtil.bb(boundingBox, 2, Color.GREEN);
        }
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        if (selection != null) {
            Block block = mc.world.getBlockState(selection).getBlock();
            if (block != Blocks.AIR) {
                mc.playerController.onPlayerDamageBlock(selection, selectionSide);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
}
