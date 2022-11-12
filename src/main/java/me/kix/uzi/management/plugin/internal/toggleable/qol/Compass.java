package me.kix.uzi.management.plugin.internal.toggleable.qol;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.render.EventRender;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Optional;

/**
 * Renders a compass onto the player's screen.
 *
 * @author Kix
 * @since 6/26/2019
 */
public class Compass extends ToggleablePlugin {

    /**
     * The item stack for a compass.
     */
    private static final ItemStack COMPASS_ITEM_STACK = new ItemStack(Items.COMPASS);

    public Compass() {
        super("Compass", Category.QOL);
    }

    @Register
    public void onRenderWorldToScreen(EventRender.WorldToScreen worldToScreen) {
        if (mc.gameSettings.showDebugInfo) {
            return;
        }
        Optional<Plugin> foundOverlay = Uzi.INSTANCE.getPluginManager().getPlugin("Overlay");
        GlStateManager.pushMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        int x = 20;
        mc.getRenderItem().renderItemIntoGUI(COMPASS_ITEM_STACK, mc.fontRenderer.getStringWidth("Uzi 1.12") + 4, -1);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.popMatrix();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }
}
