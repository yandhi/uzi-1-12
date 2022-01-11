package me.kix.uzi.management.ui.themes;

import me.kix.sodapop.components.frame.FrameContainerComponent;
import me.kix.sodapop.theme.AbstractTheme;
import me.kix.sodapop.theme.renderer.AbstractComponentRenderer;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.ui.click.component.buttons.PluginButtonContainerComponent;
import me.kix.uzi.management.ui.click.component.buttons.PropertyButtonComponent;
import me.kix.uzi.management.ui.click.component.sliders.NumberPropertySliderComponent;
import me.kix.uzi.management.ui.click.component.spinners.EnumPropertySpinnerComponent;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.CoordinatesBlockComponent;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.ToggleablesBlockComponent;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.WatermarkComponent;
import me.kix.uzi.management.ui.tab.item.impl.FolderTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.buttons.PropertyButtonTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.buttons.ToggleablePluginButtonTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.focus.SliderTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.focus.SpinnerTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.folders.ToggleablePluginFolderTabComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.List;

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
        getComponentRenderers().add(new WatermarkComponentRenderer());
        getComponentRenderers().add(new ToggleablesBlockComponentRenderer());
        getComponentRenderers().add(new CoordinatesBlockComponentRenderer());
        getComponentRenderers().add(new FolderTabComponentRenderer());
        getComponentRenderers().add(new ToggleablePluginFolderTabComponentRenderer());
        getComponentRenderers().add(new SpinnerTabComponentRenderer());
        getComponentRenderers().add(new SliderTabComponentRenderer());
        getComponentRenderers().add(new ToggleablePluginButtonTabComponentRenderer());
        getComponentRenderers().add(new PropertyButtonTabComponentRenderer());
    }

    /**
     * Renders blocks of strings :).
     */
    private class WatermarkComponentRenderer extends AbstractComponentRenderer<WatermarkComponent> {

        @Override
        public void renderComponent(WatermarkComponent component) {
            componentFont.drawStringWithShadow("Uzi", component.getRenderPosition().getX(), component.getRenderPosition().getY() + 2, Color.WHITE.getRGB());
        }
    }

    /**
     * Renders the toggleables.
     */
    private class ToggleablesBlockComponentRenderer extends AbstractComponentRenderer<ToggleablesBlockComponent> {

        @Override
        public void renderComponent(ToggleablesBlockComponent component) {
            List<ToggleablePlugin> plugins = component.getToggleables();
            plugins.sort((o1, o2) -> Float.compare(componentFont.getStringWidth(o2.getDisplay()), componentFont.getStringWidth(o1.getDisplay())));

            int y = component.getRenderPosition().getY() + 2;

            for (ToggleablePlugin plugin : plugins) {
                componentFont.drawStringWithShadow(plugin.getDisplay(), component.getRenderPosition().getX() - componentFont.getStringWidth(plugin.getDisplay()) - 2, y, plugin.getColor());
                y += componentFont.getStringHeight(plugin.getDisplay()) - 1;
            }

            /* for good measure. */
            component.getRenderPosition().setHeight(y);
        }
    }

    /**
     * Renders the coordinates
     */
    private class CoordinatesBlockComponentRenderer extends AbstractComponentRenderer<CoordinatesBlockComponent> {

        @Override
        public void renderComponent(CoordinatesBlockComponent component) {
            long x = Math.round(Minecraft.getMinecraft().player.posX);
            long y = Math.round(Minecraft.getMinecraft().player.posY);
            long z = Math.round(Minecraft.getMinecraft().player.posZ);
            String coords = String.format("%s, %s, %s", x, y, z);
            String overworld = String.format("%s, %s, %s", x * 8, y * 8, z * 8);

            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

            if (component.getRenderPosition().getY() == scaledResolution.getScaledHeight() - 20) {
                if (Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen()) {
                    component.getRenderPosition().setY(component.getRenderPosition().getY() - 16);
                }
            }

            /* If player is in the nether */
            if (Minecraft.getMinecraft().player.dimension == -1) {
                componentFont.drawStringWithShadow(coords, component.getRenderPosition().getX(), component.getRenderPosition().getY(), Color.RED.darker().darker().getRGB());
                componentFont.drawStringWithShadow(overworld, component.getRenderPosition().getX() + componentFont.getStringWidth(coords) + 2, component.getRenderPosition().getY(), Color.GRAY.getRGB());
            } else {
                componentFont.drawStringWithShadow(coords, component.getRenderPosition().getX(), component.getRenderPosition().getY(), Color.GRAY.getRGB());
            }
        }
    }

    /**
     * Tabgui folders.
     */
    private class FolderTabComponentRenderer extends AbstractComponentRenderer<FolderTabComponent> {
        @Override
        public void renderComponent(FolderTabComponent component) {
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    (component.isHovered() ? (component.isOpen() ? 0x660077FF : 0x660099FF) : Color.BLACK.getRGB()));
            componentFont.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2.5f, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (componentFont.getStringHeight(component.getName()) / 2f)) + 3f, Color.WHITE.getRGB());
        }
    }

    private class ToggleablePluginFolderTabComponentRenderer extends AbstractComponentRenderer<ToggleablePluginFolderTabComponent> {
        @Override
        public void renderComponent(ToggleablePluginFolderTabComponent component) {
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    (component.isHovered() ? (component.isOpen() ? 0x660077FF : 0x660099FF) : Color.BLACK.getRGB()));
            componentFont.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (componentFont.getStringHeight(component.getName()) / 2f)) + 3f, Color.WHITE.getRGB());
            componentFont.drawStringWithShadow("...", component.getRenderPosition().getX() +
                            component.getRenderPosition().getWidth() - componentFont.getStringWidth("...") - 2f,
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight() - componentFont.getStringHeight("...") + 3f, Color.LIGHT_GRAY.getRGB());
        }
    }

    /**
     * Tabgui focus components.
     */
    private class SpinnerTabComponentRenderer extends AbstractComponentRenderer<SpinnerTabComponent> {
        @Override
        public void renderComponent(SpinnerTabComponent component) {
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    (component.isHovered() ? 0x660099FF : Color.BLACK.getRGB()));
            componentFont.drawStringWithShadow(component.getRaw(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (componentFont.getStringHeight(component.getName()) / 2f)) + 3f, 0xFFFFFFFF);
        }
    }

    private class SliderTabComponentRenderer extends AbstractComponentRenderer<SliderTabComponent> {
        @Override
        public void renderComponent(SliderTabComponent component) {
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    Color.BLACK.getRGB());
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getSliderLength(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    component.isHovered() ? 0x660099FF : Color.BLACK.getRGB());
            componentFont.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (componentFont.getStringHeight(component.getName()) / 2f)) + 3f, 0xFFFFFFFF);
            componentFont.drawStringWithShadow(component.getProperty().getValue().toString(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth() - componentFont.getStringWidth(component.getProperty().getValue().toString()) - 2, (component.getRenderPosition().getY()
                            + (component.getRenderPosition().getHeight() / 2f) - (componentFont.getStringHeight(component.getName()) / 2f)) + 3f,
                    0xFFDEDEDE);
        }
    }

    /**
     * Tabgui buttons.
     */
    private class ToggleablePluginButtonTabComponentRenderer extends AbstractComponentRenderer<ToggleablePluginButtonTabComponent> {
        @Override
        public void renderComponent(ToggleablePluginButtonTabComponent component) {
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    (component.isHovered() ? 0x660099FF : Color.BLACK.getRGB()));
            componentFont.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (componentFont.getStringHeight(component.getName()) / 2f)) + 3f, Color.WHITE.getRGB());

            if (component.isState()) {
                RenderUtil.drawRect(component.getRenderPosition().getX() + component.getRenderPosition().getWidth() - 10,
                        component.getRenderPosition().getY(), component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                        component.getRenderPosition().getY() + component.getRenderPosition().getHeight(), 0x660077FF);
            }
        }
    }

    private class PropertyButtonTabComponentRenderer extends AbstractComponentRenderer<PropertyButtonTabComponent> {
        @Override
        public void renderComponent(PropertyButtonTabComponent component) {
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    (component.isHovered() ? 0x660099FF : Color.BLACK.getRGB()));
            componentFont.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (componentFont.getStringHeight(component.getName()) / 2f)) + 3f, Color.WHITE.getRGB());

            if (component.isState()) {
                RenderUtil.drawRect(component.getRenderPosition().getX() + component.getRenderPosition().getWidth() - 10,
                        component.getRenderPosition().getY(), component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                        component.getRenderPosition().getY() + component.getRenderPosition().getHeight(), 0x660077FF);
            }
        }
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
