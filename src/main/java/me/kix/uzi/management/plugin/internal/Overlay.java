package me.kix.uzi.management.plugin.internal;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.util.network.TPSTracker;
import me.kix.uzi.api.event.events.render.EventRender;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.util.List;
import java.util.stream.Collectors;

public class Overlay extends Plugin {

    private final Property<Boolean> branding = new Property<>("Branding", true);
    private final Property<Boolean> tps = new Property<>("TPS", true);
    private final Property<Boolean> coords = new Property<>("Coords", true);
    private final Property<Boolean> toggleables = new Property<>("Toggleables", true);

    public Overlay() {
        super("Overlay", Category.RENDER);
        getProperties().add(branding);
        getProperties().add(tps);
        getProperties().add(coords);
        getProperties().add(toggleables);
        Uzi.INSTANCE.getEventManager().register(this);
    }

    @Register
    public void onWorldToScreen(EventRender.WorldToScreen event) {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);
        if (mc.gameSettings.showDebugInfo) return;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        if (branding.getValue()) {
            mc.fontRenderer.drawStringWithShadow("Uzi 1.12", 2, 2, 0xFFFFFFFF);
        }

        if (tps.getValue()) {
            mc.fontRenderer.drawStringWithShadow(String.valueOf(TPSTracker.getTracker().getTps()), 2, 11, 0x80FFFFFF);
        }

        if (coords.getValue()) {
            long x = Math.round(mc.player.posX);
            long y = Math.round(mc.player.posY);
            long z = Math.round(mc.player.posZ);
            String coords = String.format("\2477%s, %s, %s", x, y, z);

            /* If player is in the nether */
            if (mc.player.dimension == -1) {
                coords = String.format("\247c%s \2477(%s)\2478, \247c%s \2477(%s)\2478, \247c%s \2477(%s)", x, x * 8, y, y * 8, z, z * 8);
            }

            mc.fontRenderer.drawStringWithShadow(coords, 2,
                    scaledResolution.getScaledHeight() - mc.fontRenderer.FONT_HEIGHT - (mc.ingameGUI.getChatGUI().getChatOpen() ? 14 : 2),
                    0xFFFFFFFF);
        }

        if (toggleables.getValue()) {
            int y = 2;
            List<ToggleablePlugin> toggleables = Uzi.INSTANCE.getPluginManager().getContents().stream()
                    .filter(ToggleablePlugin.class::isInstance)
                    .map(ToggleablePlugin.class::cast)
                    .filter(ToggleablePlugin::isEnabled)
                    .filter(plugin -> !plugin.isHidden())
                    .collect(Collectors.toList());
            toggleables.sort((o1, o2) -> mc.fontRenderer.getStringWidth("{" + o2.getDisplay().toLowerCase() + "}") - mc.fontRenderer.getStringWidth("{" + o1.getDisplay().toLowerCase() + "}"));

            for (ToggleablePlugin plugin : toggleables) {
                mc.fontRenderer.drawStringWithShadow("{" + plugin.getDisplay().toLowerCase() + "}", scaledResolution.getScaledWidth() - mc.fontRenderer.getStringWidth("{" + plugin.getDisplay().toLowerCase() + "}") - 2, y, plugin.getColor());
                y += mc.fontRenderer.FONT_HEIGHT;
            }
        }
        GlStateManager.popMatrix();
    }

    public Property<Boolean> getBranding() {
        return branding;
    }

    public Property<Boolean> getCoords() {
        return coords;
    }

    public Property<Boolean> getToggleables() {
        return toggleables;
    }

    public Property<Boolean> getTps() {
        return tps;
    }
}