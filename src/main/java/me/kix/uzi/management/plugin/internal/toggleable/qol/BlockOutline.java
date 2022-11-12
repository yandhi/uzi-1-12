package me.kix.uzi.management.plugin.internal.toggleable.qol;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.render.EventRender;
import me.kix.uzi.api.game.accessors.renderer.GameRenderManager;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.render.RenderUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import java.awt.*;

/**
 * A custom selection box for blocks.
 *
 * @author Kix
 * @since 7/1/2019
 */
public class BlockOutline extends ToggleablePlugin {

    public BlockOutline() {
        super("BlockOutline", Category.QOL);
        setDisplay("Block Outline");
    }

    @Register
    public void onRenderHand(EventRender.Hand hand) {
        RayTraceResult rayTraceResult = mc.objectMouseOver;
        if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos rayTracePos = rayTraceResult.getBlockPos();
            GameRenderManager renderManager = (GameRenderManager) mc.getRenderManager();
            double renderX = rayTracePos.getX() - renderManager.getRenderPosX();
            double renderY = rayTracePos.getY() - renderManager.getRenderPosY();
            double renderZ = rayTracePos.getZ() - renderManager.getRenderPosZ();
            AxisAlignedBB block = new AxisAlignedBB(0, 0, 0, 1, 1, 1).offset(renderX, renderY, renderZ);
            RenderUtil.bb(block, 0.5f, new Color(0x1EFF4E00, true));
        }
    }

}
