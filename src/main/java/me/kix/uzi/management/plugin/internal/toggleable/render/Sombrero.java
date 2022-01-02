package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.misc.EventTick;
import me.kix.uzi.api.event.events.render.EventRender;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.util.glu.Cylinder;

import java.awt.*;
import java.util.Optional;

/**
 * Similar to {@link Chinahat} but draws a sombrero instead.
 *
 * <p>
 * I made the hats stack if each other is enabled :).
 * </p>
 *
 * @author jackson
 * @since 1/2/2022
 */
public class Sombrero extends ToggleablePlugin {

    /**
     * The current hue of the hat.
     */
    private float colorHue = 0;

    public Sombrero() {
        super("Sombrero", Category.RENDER);
        setHidden(true);
    }

    @Register
    public void tick(EventTick tick) {
        colorHue += 1.5f;

        if (colorHue > 270) {
            colorHue -= 270;
        }
    }

    @Register
    public void renderHand(EventRender.Hand hand) {
        if (mc.gameSettings.thirdPersonView == 0) return;

        GlStateManager.pushMatrix();
        RenderUtil.enable3D();
        float yaw = mc.player.prevRotationYawHead + (mc.player.rotationYawHead - mc.player.prevRotationYawHead) * hand.getPartialTicks();
        GlStateManager.rotate(-yaw, 0, 1, 0);
        GlStateManager.rotate(90, 1, 0, 0);
        GlStateManager.translate(0, 0, -mc.player.eyeHeight + .2);
        GlStateManager.rotate(mc.player.rotationPitch, 1, 0, 0);
        GlStateManager.translate(0, 0, -.75);

        GlStateManager.scale(1, -1, 1);

        Optional<Plugin> chinaHatPlugin = Uzi.INSTANCE.getPluginManager().getPlugin("chinahat");

        if (chinaHatPlugin.isPresent()) {
            Chinahat chinahat = (Chinahat) chinaHatPlugin.get();
            if (chinahat.isEnabled()) {
                GlStateManager.translate(0, 0, -.38f);
            }
        }

        Color color = RenderUtil.getColorViaHue(colorHue);

        // the base
        GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        new Cylinder().draw(0.55f, 0.85f, 0.25f, 20, 20);

        // the tip
        GlStateManager.translate(0, 0, -.3);
        GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        new Cylinder().draw(.1f, 0.35f, 0.55f, 20, 20);

        RenderUtil.disable3D();
        GlStateManager.popMatrix();
    }
}
