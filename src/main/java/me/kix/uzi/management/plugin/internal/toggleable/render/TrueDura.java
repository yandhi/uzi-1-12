package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.render.EventRender;
import me.kix.uzi.api.game.accessors.item.Stack;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.util.Optional;

/**
 * A exploit that showcases the real durability of your item.
 *
 * @author Kix
 * @since 7/8/2019
 */
public class TrueDura extends ToggleablePlugin {

    public TrueDura() {
        super("TrueDura", Category.RENDER);
        setDisplay("True Dura");
    }

    @Register
    public void onRenderWorldToScreen(EventRender.WorldToScreen worldToScreen) {
        ItemStack heldStack = mc.player.getHeldItem(EnumHand.MAIN_HAND);

        Optional<Plugin> foundOverlay = Uzi.INSTANCE.getPluginManager().getPlugin("Overlay");
        int y = 2;
        if (foundOverlay.isPresent()) {
            Overlay overlay = (Overlay) foundOverlay.get();
            if (overlay.isEnabled()) {
                if (overlay.getBranding().getValue()) {
                    y += mc.fontRenderer.FONT_HEIGHT;
                }

                if (overlay.getTps().getValue()) {
                    y += mc.fontRenderer.FONT_HEIGHT;
                }
            }
        }

        if (heldStack != ItemStack.EMPTY) {
            mc.fontRenderer.drawStringWithShadow(String.valueOf(((Stack) (Object) heldStack).getTrueDurability()), 2, y, 0xFF40CC3A);
        }
    }
}
