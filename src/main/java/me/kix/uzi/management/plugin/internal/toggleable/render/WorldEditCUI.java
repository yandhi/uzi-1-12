package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.math.claim.Claim;
import me.kix.uzi.api.util.math.claim.impl.EmptyClaim;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.management.event.input.mouse.EventMousePressed;
import me.kix.uzi.management.event.render.EventRender;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import java.awt.*;

/**
 * A world edit client ui.
 *
 * <p>
 * Shows the current claim.
 * </p>
 *
 * @author Jax
 * Created in Apr 2019
 */
public class WorldEditCUI extends ToggleablePlugin {

    /**
     * The current claim for the WorldEditCUI.
     */
    private Claim currentClaim;

    /**
     * The current start and end pos.
     */
    private BlockPos currentStartPos, currentEndPos;

    public WorldEditCUI() {
        super("WorldEditCUI", Category.RENDER);
        setDisplay("World Edit CUI");
    }

    @Register
    public void onMousePressed(EventMousePressed pressed) {
        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
            switch (pressed.getMouseButton()) {
                case 0:
                    currentStartPos = mc.objectMouseOver.getBlockPos();
                    break;
                case 1:
                    currentEndPos = mc.objectMouseOver.getBlockPos();
                    break;
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
            RenderUtil.bb(renderClaim, 2f, Color.magenta);
        }
    }
}
