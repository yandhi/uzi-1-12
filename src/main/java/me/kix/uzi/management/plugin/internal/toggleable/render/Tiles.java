package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.renderer.IRenderManager;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.management.event.render.EventRender;
import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class Tiles extends ToggleablePlugin {

    private Property<Boolean> chests = new Property<>("Chests", true);
    private Property<Boolean> furnaces = new Property<>("Furnaces", false);
    private Property<Boolean> brewingStands = new Property<>("BrewingStands", false);
    private Property<Boolean> redstoneBlocks = new Property<>("RedstoneBlocks", false);

    public Tiles() {
        super("Tiles", Category.RENDER);
        setHidden(true);
    }

    @Register
    public void onRender(EventRender.Hand event) {
        for (TileEntity tile : mc.world.loadedTileEntityList) {
            double posX = tile.getPos().getX() - ((IRenderManager) mc.getRenderManager()).getRenderPosX();
            double posY = tile.getPos().getY() - ((IRenderManager) mc.getRenderManager()).getRenderPosY();
            double posZ = tile.getPos().getZ() - ((IRenderManager) mc.getRenderManager()).getRenderPosZ();
            if (tile instanceof TileEntityChest) {
                if (chests.getValue()) {
                    AxisAlignedBB bb = new AxisAlignedBB(0.0625, 0.0, 0.0625, 1, 0.875, 1).offset(posX, posY, posZ).contract(0.0625, 0, 0.0625);
                    TileEntityChest adjacent = null;
                    if (((TileEntityChest) tile).adjacentChestXNeg != null)
                        adjacent = ((TileEntityChest) tile).adjacentChestXNeg;
                    if (((TileEntityChest) tile).adjacentChestXPos != null)
                        adjacent = ((TileEntityChest) tile).adjacentChestXPos;
                    if (((TileEntityChest) tile).adjacentChestZNeg != null)
                        adjacent = ((TileEntityChest) tile).adjacentChestZNeg;
                    if (((TileEntityChest) tile).adjacentChestZPos != null)
                        adjacent = ((TileEntityChest) tile).adjacentChestZPos;
                    if (adjacent != null) {
                        bb = bb.union(new AxisAlignedBB(0.0625, 0.0, 0.0625, 1, 0.875, 1).offset(adjacent.getPos().getX() - ((IRenderManager) mc.getRenderManager()).getRenderPosX(),
                                adjacent.getPos().getY() - ((IRenderManager) mc.getRenderManager()).getRenderPosY(), adjacent.getPos().getZ() - ((IRenderManager) mc.getRenderManager()).getRenderPosZ())).contract(0.0625, 0, 0.0625);
                    }
                    if (((TileEntityChest) tile).getChestType() == BlockChest.Type.TRAP) {
                        drawBlockESP(bb, 255f, 91f, 86f, 255f, 1f);
                    } else {
                        drawBlockESP(bb, 255f, 227f, 0f, 255f, 1f);
                    }
                }
            }
            if (tile instanceof TileEntityEnderChest) {
                if (chests.getValue())
                    drawBlockESP(new AxisAlignedBB(0.0625, 0.0, 0.0625, 1, 0.875, 1).offset(posX, posY, posZ).contract(0.0625, 0, 0.0625), 78f, 197f, 255f, 255f, 1f);
            }
            if (tile instanceof TileEntityBrewingStand) {
                if (brewingStands.getValue())
                    drawBlockESP(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).offset(posX, posY, posZ), 234f, 255f, 96f, 255f, 1f);
            }
            if (tile instanceof TileEntityFurnace) {
                if (furnaces.getValue()) {
                    drawBlockESP(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).offset(posX, posY, posZ), 254f, 124f, 0f, 255f, 1f);
                }
            }

            if (tile instanceof TileEntityHopper || tile instanceof TileEntityDropper || tile instanceof TileEntityDispenser) {
                if (redstoneBlocks.getValue()) {
                    drawBlockESP(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).offset(posX, posY, posZ), 161f, 161f, 161f, 255f, 1f);
                }
            }
        }
    }

    private void drawBlockESP(AxisAlignedBB bb, float red, float green, float blue, float alpha, float width) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(width);
        GL11.glColor4f(red / 255f, green / 255f, blue / 255f, alpha / 255f);
        RenderUtil.drawOutlinedBox(bb);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }

}
