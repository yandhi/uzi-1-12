package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.input.key.EventKeyPressed;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.EnumProperty;
import me.kix.uzi.api.util.network.TPSTracker;
import me.kix.uzi.api.event.events.render.EventRender;
import me.kix.uzi.management.ui.tab.TabGui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.util.List;
import java.util.stream.Collectors;

public class Overlay extends ToggleablePlugin {

    private final EnumProperty<Brand> brand = new EnumProperty<>("Brand", Brand.UZI);
    private final EnumProperty<TogglesStyle> togglesStyle = new EnumProperty<>("Toggles", TogglesStyle.NORMAL);
    private final Property<Boolean> branding = new Property<>("Branding", true);
    private final Property<Boolean> version = new Property<>("Version", true);
    private final Property<Boolean> tps = new Property<>("TPS", true);
    private final Property<Boolean> coords = new Property<>("Coords", true);
    private final Property<Boolean> toggleables = new Property<>("Toggleables", true);
    private final Property<Boolean> tabGui = new Property<>("Tabgui", false);
    private TabGui tab;

    public Overlay() {
        super("Overlay", Category.RENDER);
        setHidden(true);
        getProperties().add(brand);
        getProperties().add(togglesStyle);
        getProperties().add(branding);
        getProperties().add(version);
        getProperties().add(tps);
        getProperties().add(coords);
        getProperties().add(toggleables);
        getProperties().add(tabGui);
    }

    @Register
    public void onWorldToScreen(EventRender.WorldToScreen event) {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);
        if (mc.gameSettings.showDebugInfo) return;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        if (branding.getValue()) {
            mc.fontRenderer.drawStringWithShadow(brand.getValue().name, 2, 2, 0xFFFFFFFF);
        }

        if (version.getValue()) {
            mc.fontRenderer.drawStringWithShadow(Uzi.INSTANCE.getVersion(), branding.getValue() ? mc.fontRenderer.getStringWidth(brand.getValue().name) + 4 : 2, 2, brand.getValue().versionColor);
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

            if(!mc.player.getActivePotionEffects().isEmpty()) {
                y = 26;
            }

            List<ToggleablePlugin> toggleables = Uzi.INSTANCE.getPluginManager().getContents().stream()
                    .filter(ToggleablePlugin.class::isInstance)
                    .map(ToggleablePlugin.class::cast)
                    .filter(ToggleablePlugin::isEnabled)
                    .filter(plugin -> !plugin.isHidden())
                    .collect(Collectors.toList());

            toggleables.sort((o1, o2) -> mc.fontRenderer.getStringWidth(getToggleName(o2)) - mc.fontRenderer.getStringWidth(getToggleName(o1)));

            for (ToggleablePlugin plugin : toggleables) {
                mc.fontRenderer.drawStringWithShadow(getToggleName(plugin), scaledResolution.getScaledWidth() - mc.fontRenderer.getStringWidth(getToggleName(plugin)) - 2, y, plugin.getColor());
                y += mc.fontRenderer.FONT_HEIGHT;
            }
        }

        if (tabGui.getValue()) {
            if (tab == null) {
                tab = TabGui.INSTANCE;
                tab.setup();
            }

            tab.draw(2, 20, 8, 12, getBrand().getValue().accentColor, 0x80000000);
        }

        GlStateManager.popMatrix();
    }

    @Register
    public void onKeyPress(EventKeyPressed pressed) {
        if (tabGui.getValue()) {
            if (tab != null) {
                tab.handleKeys(pressed.getKey());
            }
        }
    }

    /**
     * Automatically sets up the plugin's name to the right style.
     *
     * @param plugin The plugin being mutated.
     * @return The name of the plugin based on the current toggles style.
     */
    private String getToggleName(ToggleablePlugin plugin) {
        String name = plugin.getDisplay();
        switch (togglesStyle.getValue()) {
            case NORMAL:
                name = plugin.getDisplay();
                break;
            case LOWERCASE:
                name = plugin.getDisplay().toLowerCase();
                break;
            case CUB:
                name = "[" + plugin.getDisplay().toLowerCase() + "]";
                break;
            case EDGY:
                name = "{" + plugin.getDisplay().toLowerCase() + "}";
                break;
            case FUNCTION:
                name = "(" + plugin.getDisplay().toLowerCase() + ")";
                break;
        }
        return name;
    }

    public int getAccentColor() {
        return brand.getValue().getAccentColor();
    }

    public EnumProperty<Brand> getBrand() {
        return brand;
    }

    public EnumProperty<TogglesStyle> getTogglesStyle() {
        return togglesStyle;
    }

    public Property<Boolean> getBranding() {
        return branding;
    }

    public Property<Boolean> getVersion() {
        return version;
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

    /**
     * The branding of the client.
     */
    private enum Brand {
        UZI("Uzi", 0xFFBF3F46, 0xFF993940),
        DAWN("Dawn", 0xFF9F69D9, 0xFF9F69D9),
        GLOCK("Glock", 0xFFCCCCCC, 0xFFA8A8A8);

        /**
         * The actual brand name.
         */
        private final String name;

        /**
         * The color of the version text.
         */
        private final int versionColor;

        /**
         * The color for useful hud accents.
         */
        private final int accentColor;

        Brand(String name, int versionColor, int accentColor) {
            this.name = name;
            this.versionColor = versionColor;
            this.accentColor = accentColor;
        }

        public String getName() {
            return name;
        }

        public int getAccentColor() {
            return accentColor;
        }

        public int getVersionColor() {
            return versionColor;
        }
    }

    /**
     * The styling for the toggles list.
     */
    private enum TogglesStyle {
        NORMAL,
        LOWERCASE,
        CUB,
        EDGY,
        FUNCTION
    }

}