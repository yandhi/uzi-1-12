package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.render.EventRenderBlockModel;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import net.minecraft.block.BlockPistonExtension;

/**
 * Stops the client from rendering piston stems.
 *
 * @author Kix
 * @since 7/1/2019
 */
public class PistonStems extends ToggleablePlugin {

    public PistonStems() {
        super("PistonStems", Category.RENDER);
        setDisplay("Piston Stems");
    }

    @Register
    public void onRenderBlockModel(EventRenderBlockModel renderBlockModel) {
        if (renderBlockModel.getState().getBlock() instanceof BlockPistonExtension) {
            renderBlockModel.setCancelled(true);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.renderGlobal.loadRenderers();
    }
}
