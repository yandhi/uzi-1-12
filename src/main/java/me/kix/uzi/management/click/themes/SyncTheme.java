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

/**
 * The theme that latch ripped off.
 *
 * <p>
 * This is based on Rederpz's client Sync.
 * </p>
 *
 * @author Kix
 * @since 6/28/2019
 */
public class SyncTheme extends AbstractTheme {

    /**
     * The font for the title bars.
     */
    private final NahrFont titleFont = new NahrFont("Segoe UI", 20);

    /**
     * The font for the components.
     */
    private final NahrFont componentFont = new NahrFont("Lucida Console Regular", 16);

    public SyncTheme() {
        super("Sync", 120, 16, 14, 2, 2);
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

            RenderUtil.border(position.getX(), position.getY(), position.getX() + position.getWidth(),
                    position.getY() + frameHeight, 2f, 0x55000000);
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + position.getWidth(),
                    position.getY() + frameHeight, 0xDD323232, 0xDD121212);
            RenderUtil.border(position.getX() + position.getWidth() - 14, position.getY() + 2,
                    position.getX() + position.getWidth() - 2, position.getY() + position.getHeight() - 2, 2f, 0x55000000);
            RenderUtil.verticalGradientRectangle(position.getX() + position.getWidth() - 14, position.getY() + 2,
                    position.getX() + position.getWidth() - 2, position.getY() + position.getHeight() - 2, component.isExtended() ? 0x660099FF : 0xBB323232, component.isExtended() ? 0x660077FF : 0xBB121212);
            titleFont.drawString(component.getName(), position.getX() + 2, position.getY() + 3, NahrFont.FontType.SHADOW_THIN, 0xFFFFFFFF, 0xFF000000);
        }
    }

    /**
     * The component renderer for plugin buttons.
     */
    private class PluginButtonComponentRenderer extends AbstractComponentRenderer<PluginButtonContainerComponent> {
        @Override
        public void renderComponent(PluginButtonContainerComponent component) {
            Rectangle position = component.getRenderPosition();

            RenderUtil.border(position.getX(), position.getY(), position.getX() + position.getWidth(),
                    position.getY() + position.getHeight(), 2f, 0x55000000);
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + position.getWidth(),
                    position.getY() + position.getHeight(), component.getPlugin().isEnabled() ? 0x660099FF : 0x66232323, component.getPlugin().isEnabled() ? 0x660077FF : 0x66232323);
            componentFont.drawString(component.getName(), position.getX() + 2, position.getY() + 4, NahrFont.FontType.SHADOW_THIN, component.getPlugin().isEnabled() ? 0xFFFFFFFF : 0xFFBBBBBB, 0xFF000000);
        }
    }

    /**
     * The component renderer for property buttons.
     */
    private class PropertyButtonComponentRenderer extends AbstractComponentRenderer<PropertyButtonComponent> {
        @Override
        public void renderComponent(PropertyButtonComponent component) {
            Rectangle position = component.getRenderPosition();
            RenderUtil.border(position.getX() + 2, position.getY() + 2,
                    position.getX() + 12, position.getY() + position.getHeight() - 2, 2f, 0x55000000);
            RenderUtil.verticalGradientRectangle(position.getX() + 2, position.getY() + 2,
                    position.getX() + 12, position.getY() + position.getHeight() - 2,
                    component.getProperty().getValue() ? 0x660099FF : 0x66232323, component.getProperty().getValue() ? 0x660077FF : 0x66232323);
            componentFont.drawString(component.getName(), position.getX() + 14,
                    position.getY() + 5, NahrFont.FontType.SHADOW_THIN, component.getProperty().getValue() ? 0xFFFFFFFF : 0xFFBBBBBB, 0xFF000000);
        }
    }

    /**
     * The component renderer for property spinners.
     */
    private class EnumPropertySpinnerComponentRenderer extends AbstractComponentRenderer<EnumPropertySpinnerComponent> {
        @Override
        public void renderComponent(EnumPropertySpinnerComponent component) {
            Rectangle position = component.getRenderPosition();

            RenderUtil.border(position.getX(), position.getY(), position.getX() + position.getWidth(),
                    position.getY() + position.getHeight(), 2f, 0x55000000);
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + position.getWidth(),
                    position.getY() + position.getHeight(), 0x66232323, 0x66232323);
            componentFont.drawString(component.getName(), position.getX() + 2, position.getY() + 4, NahrFont.FontType.SHADOW_THIN, 0xFFBBBBBB, 0xFF000000);
            componentFont.drawString(component.getProperty().getFixedValue(), position.getX() + position.getWidth() - componentFont.getStringWidth(component.getProperty().getFixedValue()) - 2, position.getY() + 5, NahrFont.FontType.SHADOW_THIN, 0xFFBBBBBB, 0xFF000000);
        }
    }

    /**
     * The component renderer for property sliders.
     */
    private class NumberPropertySliderComponentRenderer extends AbstractComponentRenderer<NumberPropertySliderComponent> {
        @Override
        public void renderComponent(NumberPropertySliderComponent component) {
            Rectangle position = component.getRenderPosition();
            RenderUtil.border(position.getX(), position.getY(), position.getX() + position.getWidth(),
                    position.getY() + position.getHeight(), 2f, 0x55000000);
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + position.getWidth(),
                    position.getY() + position.getHeight(), 0x66232323, 0x66232323);
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + component.getLength(),
                    position.getY() + position.getHeight(), 0x660099FF, 0x660077FF);
            componentFont.drawString(component.getName(), position.getX() + 2, position.getY() + 4, NahrFont.FontType.SHADOW_THIN, 0xFFBBBBBB, 0xFF000000);
            componentFont.drawString(component.getProperty().getValue().toString(), position.getX() + position.getWidth() - componentFont.getStringWidth(component.getProperty().getValue().toString()) - 2, position.getY() + 5, NahrFont.FontType.SHADOW_THIN, 0xFFBBBBBB, 0xFF000000);
        }
    }

}
