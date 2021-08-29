package me.kix.uzi.management.plugin.internal.toggleable.render.ui.themes;

import me.kix.sodapop.theme.AbstractTheme;
import me.kix.sodapop.theme.renderer.AbstractComponentRenderer;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.StringBlockComponent;


import java.awt.*;

/**
 * The main theme for the client.
 *
 * @author yandhi
 * @since 8/29/2021
 */
public class UziTheme extends AbstractTheme {

    /**
     * The font renderer for the UI.
     */
    private final NahrFont font = new NahrFont("Jetbrains Mono", 16);

    public UziTheme() {
        super("Uzi", 0, 0, 0, 0, 0);
    }

    @Override
    public void initTheme() {
        getComponentRenderers().add(new StringBlockComponentRenderer());
    }


    /**
     * Renders blocks of strings :).
     */
    private class StringBlockComponentRenderer extends AbstractComponentRenderer<StringBlockComponent> {

        @Override
        public void renderComponent(StringBlockComponent component) {
            font.drawStringWithShadow(component.getName(), component.getRenderPosition().getX(), component.getRenderPosition().getY(), Color.WHITE.getRGB());
        }
    }
}
