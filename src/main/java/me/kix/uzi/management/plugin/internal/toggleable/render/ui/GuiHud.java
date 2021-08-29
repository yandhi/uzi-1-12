package me.kix.uzi.management.plugin.internal.toggleable.render.ui;

import me.kix.sodapop.Component;
import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.theme.Theme;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.management.click.themes.UziTheme;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.StringBlockComponent;

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
        components.add(new StringBlockComponent("Uzi", this, new Rectangle(2, 2, 25, 25)));
    }

    /**
     * Renders the HUD to wherever need be.
     */
    public void render() {
        components.forEach(component -> component.drawComponent(getTheme().getFactory().getComponentRenderer(component), 0, 0, 0));
    }

    @Override
    public Theme getTheme() {
        return theme;
    }
}
