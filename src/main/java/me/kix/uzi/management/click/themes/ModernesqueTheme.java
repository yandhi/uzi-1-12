package me.kix.uzi.management.click.themes;

import me.kix.sodapop.components.frame.FrameContainerComponent;
import me.kix.sodapop.theme.AbstractTheme;
import me.kix.sodapop.theme.renderer.AbstractComponentRenderer;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.click.component.buttons.PluginButtonContainerComponent;
import me.kix.uzi.management.click.component.buttons.PropertyButtonComponent;
import me.kix.uzi.management.click.component.sliders.NumberPropertySliderComponent;
import me.kix.uzi.management.click.component.spinners.EnumPropertySpinnerComponent;
import org.apache.commons.lang3.text.WordUtils;

/**
 * An extremely modern theme.
 *
 * @author Kix
 * @since 7/12/2019
 */
public class ModernesqueTheme extends AbstractTheme {

    /**
     * The font for the title.
     */
    private final NahrFont titleFont = new NahrFont("Arial", 20);

    /**
     * The font for the components.
     */
    private final NahrFont componentFont = new NahrFont("Arial", 18);

    public ModernesqueTheme() {
        super("Modernesque", 95, 20, 16, 4, 4);
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
            int frameHeight = position.getHeight();
            if (component.isExtended()) {
                frameHeight += component.getMaxHeight() + 2;
            }
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), component.isExtended() ? 0xFF40B56F : 0xFF2B2C31);
            RenderUtil.drawRect(position.getX(), position.getY() + 3, position.getX() + position.getWidth(), position.getY() + frameHeight, 0xFF18191C);

            titleFont.drawStringWithShadow(component.getName(), position.getX() + 2, position.getY() + 7, 0xFFFAFAFB);
        }
    }

    /**
     * The component renderer for plugin buttons.
     */
    private class PluginButtonComponentRenderer extends AbstractComponentRenderer<PluginButtonContainerComponent> {
        @Override
        public void renderComponent(PluginButtonContainerComponent component) {
            Rectangle position = component.getRenderPosition();

            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), component.getPlugin().isEnabled() ? 0xFF40B56F : 0xFF2B2C31);
            componentFont.drawStringWithShadow(component.getName(), position.getX() + (position.getWidth() / 2f) - (componentFont.getStringWidth(component.getName()) / 2), position.getY() + 5, 0xFFFAFAFB);
        }
    }

    /**
     * The component renderer for property buttons.
     */
    private class PropertyButtonComponentRenderer extends AbstractComponentRenderer<PropertyButtonComponent> {
        @Override
        public void renderComponent(PropertyButtonComponent component) {
            Rectangle position = component.getRenderPosition();

            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), component.getProperty().getValue() ? 0xFF40B56F : 0xFF2B2C31);
            componentFont.drawStringWithShadow(component.getName(), position.getX() + (position.getWidth() / 2f) - (componentFont.getStringWidth(component.getName()) / 2), position.getY() + 5, 0xFFFAFAFB);
        }
    }

    /**
     * The component renderer for property spinners.
     */
    private class EnumPropertySpinnerComponentRenderer extends AbstractComponentRenderer<EnumPropertySpinnerComponent> {
        @Override
        public void renderComponent(EnumPropertySpinnerComponent component) {
            Rectangle position = component.getRenderPosition();

            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), 0xFF40B56F);
            componentFont.drawStringWithShadow(WordUtils.capitalizeFully(component.getProperty().getFixedValue()), position.getX() + (position.getWidth() / 2f) - (componentFont.getStringWidth(WordUtils.capitalizeFully(component.getProperty().getFixedValue())) / 2), position.getY() + 5, 0xFFFAFAFB);
        }
    }

    /**
     * The component renderer for property sliders.
     */
    private class NumberPropertySliderComponentRenderer extends AbstractComponentRenderer<NumberPropertySliderComponent> {
        @Override
        public void renderComponent(NumberPropertySliderComponent component) {
            Rectangle position = component.getRenderPosition();

            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), 0xFF2B2C31);
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + component.getLength(), position.getY() + position.getHeight(), 0xFF40B56F);
            componentFont.drawStringWithShadow(component.getProperty().getLabel() + " " + component.getProperty().getValue().toString(), position.getX() + (position.getWidth() / 2f) - (componentFont.getStringWidth(component.getProperty().getLabel() + " " + component.getProperty().getValue().toString()) / 2), position.getY() + 5, 0xFFFAFAFB);
        }
    }
}
