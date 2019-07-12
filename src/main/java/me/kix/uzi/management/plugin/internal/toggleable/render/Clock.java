package me.kix.uzi.management.plugin.internal.toggleable.render;

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
 * Renders a clock onto the player's screen.
 *
 * @author Kix
 * @since 6/26/2019
 */
public class Clock extends ToggleablePlugin {

    /**
     * The item stack for a clock.
     */
    private static final ItemStack CLOCK_ITEM_STACK = new ItemStack(Items.CLOCK);

    public Clock() {
        super("Clock", Category.RENDER);
    }

    @Register
    public void onRenderWorldToScreen(EventRender.WorldToScreen worldToScreen) {
        if (mc.gameSettings.showDebugInfo) {
            return;
        }
        Optional<Plugin> foundOverlay = Uzi.INSTANCE.getPluginManager().getPlugin("Overlay");
        Optional<Plugin> foundCompass = Uzi.INSTANCE.getPluginManager().getPlugin("Compass");
        GlStateManager.pushMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        int x = 4;
        if (foundOverlay.isPresent()) {
            Overlay overlay = (Overlay) foundOverlay.get();
            if (overlay.isEnabled()) {
                if (overlay.getBranding().getValue()) {
                    x += mc.fontRenderer.getStringWidth("Uzi") + 4;
                }

                if (overlay.getVersion().getValue()) {
                    x += mc.fontRenderer.getStringWidth(Uzi.INSTANCE.getVersion());
                }
            }
        }

        if (foundCompass.isPresent()) {
            Compass compass = (Compass) foundCompass.get();
            if (compass.isEnabled()) {
                x += 12;
            }
        }
        mc.getRenderItem().renderItemIntoGUI(CLOCK_ITEM_STACK, x, -1);
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
