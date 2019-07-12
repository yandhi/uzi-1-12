package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.render.EventRenderBlockModel;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import net.minecraft.block.BlockFalling;

/**
 * Prevents the player from seeing the physics engine taking effect and making blocks fall.
 *
 * @author Kix
 * @since 7/1/2019
 */
public class AntiPhysics extends ToggleablePlugin {

    public AntiPhysics() {
        super("AntiPhysics", Category.RENDER);
        setDisplay("Anti Physics");
    }

    @Register
    public void onRenderBlockModel(EventRenderBlockModel renderBlockModel) {
        if (renderBlockModel.getState().getBlock() instanceof BlockFalling) {
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
