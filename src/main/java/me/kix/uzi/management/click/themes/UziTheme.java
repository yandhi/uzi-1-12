package me.kix.uzi.management.click.themes;

import me.kix.sodapop.components.frame.FrameContainerComponent;
import me.kix.sodapop.theme.AbstractTheme;
import me.kix.sodapop.theme.renderer.AbstractComponentRenderer;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.Uzi;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.click.component.buttons.PluginButtonContainerComponent;
import me.kix.uzi.management.click.component.buttons.PropertyButtonComponent;
import me.kix.uzi.management.click.component.sliders.NumberPropertySliderComponent;
import me.kix.uzi.management.click.component.spinners.EnumPropertySpinnerComponent;
import me.kix.uzi.management.plugin.internal.toggleable.render.Overlay;

import java.util.Optional;

/**
 * The uzi version of the noil styled ui.
 *
 * @author Kix
 * @since 6/27/2019
 */
public class UziTheme extends AbstractTheme {

    /**
     * The font for title panels.
     */
    private final NahrFont titleFont = new NahrFont("Verdana", 12);

    /**
     * The current accent color of the theme.
     */
    private int accentColor = 0xFF993940;

    public UziTheme() {
        super("Uzi", 100, 9, 10, 2, 2);
    }

    @Override
    public void initTheme() {
        getComponentRenderers().add(new FrameComponentRenderer());
        getComponentRenderers().add(new PluginButtonComponentRenderer());
        getComponentRenderers().add(new PropertyButtonComponentRenderer());
        getComponentRenderers().add(new EnumPropertySpinnerComponentRenderer());
        getComponentRenderers().add(new NumberPropertySliderComponentRenderer());
    }

    /**
     * The component renderer for the frame.
     */
    private class FrameComponentRenderer extends AbstractComponentRenderer<FrameContainerComponent> {
        @Override
        public void renderComponent(FrameContainerComponent component) {
            Rectangle position = component.getRenderPosition();

            Optional<Plugin> foundOverlay = Uzi.INSTANCE.getPluginManager().getPlugin("Overlay");
            if (foundOverlay.isPresent()) {
                Overlay overlay = (Overlay) foundOverlay.get();

                if (overlay.isEnabled()) {
                    accentColor = overlay.getAccentColor();
                }
            }
            int frameHeight = position.getHeight();
            if (component.isExtended()) {
                frameHeight += component.getMaxHeight() + 2;
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
            RenderUtil.drawRect(position.getX() + 1, position.getY() + 1, position.getX() + 9, position.getY() + position.getHeight() - 1, component.getPlugin().isEnabled() ? accentColor : 0xFF101010);
            titleFont.drawString(component.getName(), position.getX() + 11, position.getY() + 4f, NahrFont.FontType.SHADOW_THICK, 0xFF838383, 0xFF121212);

            if (!component.getComponents().isEmpty()) {
                titleFont.drawString("..", position.getX() + position.getWidth() - titleFont.getStringWidth("..") - 2, position.getY() + 4f, NahrFont.FontType.SHADOW_THICK, component.isExtended() ? accentColor : 0xFF838383, 0xFF121212);
            }
        }
    }

    /**
     * The component renderer for property buttons.
     */
    private class PropertyButtonComponentRenderer extends AbstractComponentRenderer<PropertyButtonComponent> {
        @Override
        public void renderComponent(PropertyButtonComponent component) {
            Rectangle position = component.getRenderPosition();
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), component.getProperty().getValue() ? accentColor : 0xFF1F1F1F);
            RenderUtil.drawRect(position.getX() + 10, position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight() - 1, 0xFF1F1F1F);
            titleFont.drawString(component.getName(), position.getX() + 12, position.getY() + 4f, NahrFont.FontType.SHADOW_THICK, 0xFF838383, 0xFF121212);
        }
    }

    /**
     * The component renderer for property spinners.
     */
    private class EnumPropertySpinnerComponentRenderer extends AbstractComponentRenderer<EnumPropertySpinnerComponent> {
        @Override
        public void renderComponent(EnumPropertySpinnerComponent component) {
            Rectangle position = component.getRenderPosition();
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), 0xFF1F1F1F);
            titleFont.drawString(component.getName(), position.getX() + 2, position.getY() + 4f, NahrFont.FontType.SHADOW_THIN, 0xFF565656, 0xFF131313);
            titleFont.drawString(component.getProperty().getFixedValue(),
                    position.getX() + position.getWidth() - titleFont.getStringWidth(component.getProperty().getFixedValue()) - 2,
                    position.getY() + 4f, NahrFont.FontType.SHADOW_THIN, 0xFF565656, 0xFF131313);
        }
    }

    /**
     * The component renderer for property sliders.
     */
    private class NumberPropertySliderComponentRenderer extends AbstractComponentRenderer<NumberPropertySliderComponent> {
        @Override
        public void renderComponent(NumberPropertySliderComponent component) {
            Rectangle position = component.getRenderPosition();
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), 0xFF1F1F1F);
            RenderUtil.drawRect(position.getX() + component.getLength() - 5, position.getY(), position.getX() + component.getLength(), position.getY() + position.getHeight(), accentColor);
            titleFont.drawString(component.getName(), position.getX() + 2, position.getY() + 4f, NahrFont.FontType.SHADOW_THICK, 0xFF838383, 0xFF121212);
            titleFont.drawString(component.getProperty().getValue().toString(),
                    position.getX() + position.getWidth() - titleFont.getStringWidth(component.getProperty().getValue().toString()) - 2,
                    position.getY() + 4f, NahrFont.FontType.SHADOW_THICK, 0xFF838383, 0xFF121212);
        }
    }
}