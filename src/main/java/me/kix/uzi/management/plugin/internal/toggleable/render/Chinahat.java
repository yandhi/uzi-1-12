package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.misc.EventTick;
import me.kix.uzi.api.event.events.render.EventRender;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.render.RainbowUtil;
import me.kix.uzi.api.util.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.util.glu.Cylinder;

import java.awt.*;

/**
 * I saw this on the rise showcase video, knew it was skidded and found where it was from, so here's my implementation into Uzi!
 *
 * <p>
 * Google search chinahat and its the first gist, but its just basic gl shapes.
 * </p>
 *
 * @author jackson
 * @since 1/2/2022
 */
public class Chinahat extends ToggleablePlugin {

    public Chinahat() {
        super("Chinahat", Category.RENDER);
        setDisplay("China Hat");
        setHidden(true);
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


        Color color = RainbowUtil.INSTANCE.getColor();
        GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        new Cylinder().draw(0, 0.65f, 0.35f, 20, 20);

        RenderUtil.disable3D();
        GlStateManager.popMatrix();
    }
}
