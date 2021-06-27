package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.input.mouse.EventMousePressed;
import me.kix.uzi.api.event.events.render.EventRender;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.math.claim.Claim;
import me.kix.uzi.api.util.render.RenderUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import java.awt.*;

/**
 * Helps the player build by calculating useful information within given bounds.
 *
 * @author Kix
 * @since 6/30/2019
 */
public class BuildHelper extends ToggleablePlugin {

    /**
     * The current claim for the WorldEditCUI.
     */
    private Claim currentClaim;

    /**
     * The current start and end pos.
     */
    private BlockPos currentStartPos, currentEndPos;

    public BuildHelper() {
        super("BuildHelper", Category.RENDER);
        setDisplay("Build Helper");
    }

    @Register
    public void onMousePressed(EventMousePressed mousePressed) {
        if (mc.world != null) {
            final EntityPlayerSP player = mc.player;
            if (player.getHeldItem(EnumHand.MAIN_HAND) == ItemStack.EMPTY) {
                if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
                    switch (mousePressed.getMouseButton()) {
                        case 0:
                            currentStartPos = mc.objectMouseOver.getBlockPos();
                            break;
                        case 1:
                            currentEndPos = mc.objectMouseOver.getBlockPos();
                            break;
                    }
                }
            }
            if (currentStartPos != null && currentEndPos != null) {
                if (currentClaim == null) {
                    currentClaim = new Claim(currentStartPos, currentEndPos);
                } else {
                    currentClaim.setStartPos(currentStartPos);
                    currentClaim.setEndPos(currentEndPos);
                }
            }
        }
    }

    @Register
    public void onRenderHand(EventRender.Hand hand) {
        if (currentClaim != null) {
            int xDiff = currentClaim.getEndPos().getX() - currentClaim.getStartPos().getX();
            int yDiff = currentClaim.getEndPos().getY() - currentClaim.getStartPos().getY();
            int zDiff = currentClaim.getEndPos().getZ() - currentClaim.getStartPos().getZ();
            double renderX = currentClaim.getStartPos().getX() - mc.getRenderManager().viewerPosX;
            double renderY = currentClaim.getStartPos().getY() - mc.getRenderManager().viewerPosY;
            double renderZ = currentClaim.getStartPos().getZ() - mc.getRenderManager().viewerPosZ;
            AxisAlignedBB renderClaim = new AxisAlignedBB(0, 0, 0, xDiff, yDiff, zDiff).offset(renderX, renderY, renderZ);
            RenderUtil.bb(renderClaim, 1f, new Color(0x2CB247EC, true));


            AxisAlignedBB centerPoint = new AxisAlignedBB(0, 0, 0, 1, 1, 1).offset(renderX + Math.floor(xDiff / 2), renderY + Math.floor(yDiff / 2), renderZ + Math.floor(zDiff / 2));
            RenderUtil.bb(centerPoint, 1f, new Color(0xB5ECC847, true));
        }
    }
}
