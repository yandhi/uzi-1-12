package me.kix.uzi.management.click.themes;

import me.kix.uzi.api.ui.toolkit.components.frame.FrameContainerComponent;
import me.kix.uzi.api.ui.toolkit.theme.AbstractTheme;
import me.kix.uzi.api.ui.toolkit.theme.renderer.AbstractComponentRenderer;
import me.kix.uzi.api.ui.toolkit.util.Rectangle;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.click.component.buttons.PluginButtonContainerComponent;

/**
 * The noil-ui styled theme for the click framework.
 *
 * @author Kix
 * @since 6/27/2019
 */
public class NoilTheme extends AbstractTheme {

    /**
     * The font for title panels.
     */
    private final NahrFont titleFont = new NahrFont("Verdana", 12);

    public NoilTheme() {
        super("noil", 100, 9, 10, 2, 2);
    }

    @Override
    public void initTheme() {
        getComponentRenderers().add(new FrameComponentRenderer());
        getComponentRenderers().add(new PluginButtonComponentRenderer());
    }

    /**
     * The component renderer for the frame.
     */
    private class FrameComponentRenderer extends AbstractComponentRenderer<FrameContainerComponent> {
        @Override
        public void renderComponent(FrameContainerComponent component) {
            Rectangle position = component.getRenderPosition();
            int frameHeight = position.getHeight();
            if (component.isExtended()) {
                frameHeight += component.getFunctionalPosition().getHeight();
            }
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + frameHeight, 0xFF101010);
            titleFont.drawString(component.getName(), position.getX() + 2, position.getY() + 3.5f, NahrFont.FontType.NORMAL, 0xFF565656, -1);
        }
    }

    /**
     * The component renderer for plugin buttons.
     */
    private class PluginButtonComponentRenderer extends AbstractComponentRenderer<PluginButtonContainerComponent> {
        @Override
        public void renderComponent(PluginButtonContainerComponent component) {
            Rectangle position = component.getRenderPosition();
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), 0xFF1F1F1F);
            RenderUtil.drawRect(position.getX() + 1, position.getY() + 1, position.getX() + 9, position.getY() + position.getHeight() - 1, component.getPlugin().isEnabled() ? 0xFF373737 : 0xFF101010);
            titleFont.drawString(component.getName().toLowerCase(), position.getX() + 11, position.getY() + 4f, NahrFont.FontType.SHADOW_THICK, 0xFF838383, 0xFF121212);
        }
    }
}
