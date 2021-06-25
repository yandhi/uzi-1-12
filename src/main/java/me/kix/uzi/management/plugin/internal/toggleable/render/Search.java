package me.kix.uzi.management.plugin.internal.toggleable.render;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.render.EventRender;
import me.kix.uzi.api.event.events.render.EventRenderBlockModel;
import me.kix.uzi.api.game.accessors.renderer.GameRenderManager;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.render.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Searches for specified blocks and highlights them.
 *
 * <p>
 * This works by getting the blocks position from the rendering process
 * and then using that in cache in order to render our outline.
 * </p>
 *
 * @author Kix
 * @since 6/28/2019
 */
public class Search extends ToggleablePlugin {

    /**
     * The maximum distance to a block.
     */
    private final NumberProperty<Float> distance = new NumberProperty<>("Distance", 50f, 5f, 200f, 10f);

    /**
     * The blocks to render.
     */
    private final Set<Block> blocks = new HashSet<>();

    /**
     * The cached blocks.
     */
    private final Set<BlockPos> cache = new HashSet<>();

    public Search() {
        super("Search", Category.RENDER);
    }

    @Override
    public void initPlugin() {
        super.initPlugin();
        getProperties().add(distance);
    }

    @Override
    public void load(JsonObject source) {
        super.load(source);
        if (source.has("Blocks")) {
            JsonArray jsonArray = source.getAsJsonArray("Blocks");
            jsonArray.forEach(element -> {
                Block block = Block.getBlockFromName(element.getAsString());
                if (block != null) {
                    blocks.add(block);
                }
            });
        }
    }

    @Override
    public void save(JsonObject destination) {
        super.save(destination);
        JsonArray blocksArray = new JsonArray();
        getBlocks().forEach(block -> blocksArray.add(new JsonPrimitive(block.getLocalizedName())));
        destination.add("Blocks", blocksArray);
    }

    @Register
    public void onRenderBlockModel(EventRenderBlockModel renderBlockModel) {
        Block block = renderBlockModel.getState().getBlock();

        if (blocks.contains(block)) {
            cache.add(renderBlockModel.getPos());
        }

    }

    @Register
    public void onRenderHand(EventRender.Hand hand) {
        cache.forEach(pos -> {
            GameRenderManager renderManager = (GameRenderManager) mc.getRenderManager();
            double renderX = pos.getX() - renderManager.getRenderPosX();
            double renderY = pos.getY() - renderManager.getRenderPosY();
            double renderZ = pos.getZ() - renderManager.getRenderPosZ();

            AxisAlignedBB boundingBox = new AxisAlignedBB(0, 0, 0, 1, 1, 1).offset(renderX, renderY, renderZ);
            RenderUtil.bb(boundingBox, 0.5f, getBlockColor(mc.world.getBlockState(pos).getBlock()));
        });
    }

    /**
     * Gives the color of the block.
     *
     * @param block The block being checked for color.
     * @return The color of the block.
     */
    private Color getBlockColor(Block block) {
        if (block == Blocks.DIAMOND_ORE) {
            return Color.CYAN;
        }
        if (block == Blocks.LAPIS_ORE) {
            return Color.BLUE;
        }
        if (block == Blocks.EMERALD_ORE) {
            return Color.GREEN;
        }
        if (block == Blocks.IRON_ORE) {
            return Color.ORANGE;
        }
        if (block == Blocks.GOLD_ORE) {
            return Color.YELLOW;
        }
        if (block == Blocks.REDSTONE_ORE) {
            return Color.RED;
        }
        if (block == Blocks.COAL_ORE) {
            return Color.BLACK;
        }
        return Color.PINK;
    }

    public Set<Block> getBlocks() {
        return blocks;
    }

    public Set<BlockPos> getCache() {
        return cache;
    }
}
