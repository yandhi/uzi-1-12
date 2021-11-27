package me.kix.uzi.management.plugin.internal.toggleable.render.ui;

import me.kix.sodapop.Component;
import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.theme.Theme;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.management.click.themes.UziTheme;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.CoordinatesBlockComponent;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.StringBlockComponent;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.ToggleablesBlockComponent;
import me.kix.uzi.management.ui.tab.TabGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.HashSet;
import java.util.Set;

/**
 * The gui pass-through for sodapop and the HUD system.
 *
 * @author yandhi
 * @since 8/29/2021
 */
public class GuiHud implements GuiManager {

    /**
     * The current theme.
     */
    private final Theme theme = new UziTheme();

    /**
     * The components for the hud.
     */
    private final Set<Component> components = new HashSet<>();

    public GuiHud() {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        components.add(new StringBlockComponent("Uzi", this, new Rectangle(2, 2, 25, 25)));
        components.add(new ToggleablesBlockComponent(this, new Rectangle(scaledResolution.getScaledWidth(), 2, 50, 20)));
        components.add(new CoordinatesBlockComponent(this, new Rectangle(2, scaledResolution.getScaledHeight() - 20, 50, 30)));
    }

    /**
     * Renders the HUD to wherever need be.
     *
     * @param tabGui Whether or not to render a tabgui.
     * @param tab    The instance of the tabgui if it exists.
     */
    public void render(boolean tabGui, TabGui tab) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        components.forEach(component -> {
            if (component instanceof ToggleablesBlockComponent) {
                component.getRenderPosition().setX(scaledResolution.getScaledWidth());
            }

            if (component instanceof CoordinatesBlockComponent) {
                component.getRenderPosition().setY(scaledResolution.getScaledHeight() - (Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen() ? 26 : 10));
            }

            component.drawComponent(getTheme().getFactory().getComponentRenderer(component), 0, 0, 0);
        });

        if (tabGui) {
            tab.draw();
        }
    }

    @Override
    public Theme getTheme() {
        return theme;
    }
}
