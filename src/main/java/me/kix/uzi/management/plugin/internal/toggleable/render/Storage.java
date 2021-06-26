package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.renderer.GameRenderManager;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.util.render.Box;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.event.events.render.EventRender;
import net.minecraft.block.BlockChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Im rewriting this today i guess.
 *
 * <p>
 * This utilizes some rendering utilities from Huzuni, sorry I'm not good with Minecraft's new rendering system.
 * I hate it.
 * </p>
 *
 * <p>
 * lol im gangsta <3.
 * </p>
 *
 * @author Kix
 * @since 7/12/2019 (revised 6/26/2021)
 */
public class Storage extends ToggleablePlugin {

    /**
     * Whether or not to render chests.
     */
    private final Property<Boolean> chests = new Property<>("Chests", true);

    /**
     * Whether or not to render furnaces.
     */
    private final Property<Boolean> furnaces = new Property<>("Furnaces", false);

    /**
     * Whether or not to render brewing stands.
     */
    private final Property<Boolean> brewingStands = new Property<>("BrewingStands", false);

    /**
     * Whether or not to render redstone equipment.
     */
    private final Property<Boolean> redstoneBlocks = new Property<>("RedstoneBlocks", false);

    /**
     * Whether or not to base chest alpha on distance.
     */
    private final Property<Boolean> fading = new Property<>("Fading", true);

    /**
     * The left side of the chest.
     *
     * <p>
     * This is used strictly for aesthetics.
     * </p>
     */
    private final AxisAlignedBB leftChest = new AxisAlignedBB(.0625, 0, .0625, 1.9375, .875, .937);

    /**
     * The normal size of the chest.
     *
     * <p>
     * This used to make the bounding box appear to perfectly fit around the tile entity.
     * </p>
     */
    private final AxisAlignedBB normalChest = new AxisAlignedBB(.0625, 0, .0625, .937, .875, .937);

    /**
     * The right side of the chest.
     * <p>
     * This is used strictly for aesthetics.
     * </p>
     */
    private final AxisAlignedBB rightChest = new AxisAlignedBB(.0625, 0, .0625, .937, .875, 1.9375);

    /**
     * The box normally rendered for 1x1 tiles.
     */
    private final AxisAlignedBB normalBox = new AxisAlignedBB(0, 0, 0, 1, 1, 1);

    /**
     * The color of the chest.
     */
    private final Color chestColor = new Color(0x50CCBC64, true);

    /**
     * The color of the trapped chest.
     */
    private final Color trappedChestColor = new Color(0x50CC3C3A, true);

    /**
     * The color of the ender chest.
     */
    private final Color enderChestColor = new Color(0x505CA1CC, true);

    /**
     * The color of the furnace.
     */
    private final Color furnaceColor = new Color(0x50808080, true);

    /**
     * The color of the brewing stand.
     */
    private final Color brewingStandColor = new Color(0x50CC802A, true);

    /**
     * The color of the redstone items.
     */
    private final Color redstoneColor = new Color(0x505FCC57, true);


    public Storage() {
        super("Storage", Category.RENDER);
        setHidden(true);
        getProperties().add(chests);
        getProperties().add(furnaces);
        getProperties().add(brewingStands);
        getProperties().add(redstoneBlocks);
    }

    @Register
    public void onRender(EventRender.Hand event) {
        for (TileEntity tile : mc.world.loadedTileEntityList) {
            double posX = tile.getPos().getX() - ((GameRenderManager) mc.getRenderManager()).getRenderPosX();
            double posY = tile.getPos().getY() - ((GameRenderManager) mc.getRenderManager()).getRenderPosY();
            double posZ = tile.getPos().getZ() - ((GameRenderManager) mc.getRenderManager()).getRenderPosZ();

            GL11.glPushMatrix();
            RenderUtil.enable3D();
            GL11.glTranslated(posX, posY, posZ);

            if (tile instanceof TileEntityChest) {
                if (chests.getValue()) {
                    TileEntityChest chest = (TileEntityChest) tile;
                    boolean trapped = chest.getChestType() == BlockChest.Type.TRAP;
                    if (chest.adjacentChestXPos != null) {
                        drawBox(leftChest, tile, trapped ? trappedChestColor : chestColor);
                    } else if (chest.adjacentChestZPos != null) {
                        drawBox(rightChest, tile, trapped ? trappedChestColor : chestColor);
                    } else if (chest.adjacentChestXNeg == null && chest.adjacentChestZNeg == null) {
                        drawBox(normalChest, tile, trapped ? trappedChestColor : chestColor);
                    }
                }
            }
            if (tile instanceof TileEntityEnderChest) {
                if (chests.getValue()) {
                    drawBox(normalChest, tile, enderChestColor);
                }
            }
            if (tile instanceof TileEntityBrewingStand) {
                if (brewingStands.getValue()) {
                    drawBox(normalChest, tile, brewingStandColor);
                }
            }
            if (tile instanceof TileEntityFurnace) {
                if (furnaces.getValue()) {
                    drawBox(normalBox, tile, furnaceColor);
                }
            }

            if (tile instanceof TileEntityHopper || tile instanceof TileEntityDispenser) {
                if (redstoneBlocks.getValue()) {
                    drawBox(normalBox, tile, redstoneColor);
                }
            }
            RenderUtil.disable3D();
            GL11.glPopMatrix();
        }
    }

    /**
     * Draws the box.
     *
     * @param box    The box being drawn.
     * @param entity The entity being drawn.
     * @param color  The color of the box.
     */
    private void drawBox(AxisAlignedBB box, TileEntity entity, Color color) {
        double distance = Math.sqrt(mc.player.getDistanceSq(entity.getPos()));
        if (fading.getValue() && distance <= 80) {
            /* Simple proportion based on distance for the opacity to make it fade. */
            RenderUtil.bb(box, 1f, new Color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, (float) distance / 255f));
        } else {
            RenderUtil.bb(box, 1f, color);
        }
    }
}