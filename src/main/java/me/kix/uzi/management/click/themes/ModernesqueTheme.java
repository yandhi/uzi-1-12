package me.kix.uzi.management.click.themes;

import me.kix.sodapop.components.frame.FrameContainerComponent;
import me.kix.sodapop.theme.AbstractTheme;
import me.kix.sodapop.theme.renderer.AbstractComponentRenderer;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.render.RainbowUtil;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.click.component.buttons.PluginButtonContainerComponent;
import me.kix.uzi.management.click.component.buttons.PropertyButtonComponent;
import me.kix.uzi.management.click.component.sliders.NumberPropertySliderComponent;
import me.kix.uzi.management.click.component.spinners.EnumPropertySpinnerComponent;
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
import org.apache.commons.lang3.text.WordUtils;

import java.awt.*;
import java.util.List;

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
        super("Modernesque", 125, 20, 16, 4, 4);
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
                    (component.isHovered() ? (component.isOpen() ? RainbowUtil.INSTANCE.getColor().darker().darker().darker().getRGB() : RainbowUtil.INSTANCE.getColor().darker().darker().getRGB()) : Color.BLACK.getRGB()));
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
                    (component.isHovered() ? (component.isOpen() ? RainbowUtil.INSTANCE.getColor().darker().darker().darker().getRGB() : RainbowUtil.INSTANCE.getColor().darker().darker().getRGB()) : Color.BLACK.getRGB()));
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
                    (component.isHovered() ? 0xFF40B56F : Color.BLACK.getRGB()));
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
                    component.isHovered() ? RainbowUtil.INSTANCE.getColor().darker().darker().getRGB() : Color.BLACK.getRGB());
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
                    (component.isHovered() ? RainbowUtil.INSTANCE.getColor().darker().getRGB() : Color.BLACK.getRGB()));
            componentFont.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (componentFont.getStringHeight(component.getName()) / 2f)) + 3f, Color.WHITE.getRGB());

            if (component.isState()) {
                RenderUtil.drawRect(component.getRenderPosition().getX() + component.getRenderPosition().getWidth() - 10,
                        component.getRenderPosition().getY(), component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                        component.getRenderPosition().getY() + component.getRenderPosition().getHeight(), RainbowUtil.INSTANCE.getColor().darker().darker().darker().getRGB());
            }
        }
    }

    private class PropertyButtonTabComponentRenderer extends AbstractComponentRenderer<PropertyButtonTabComponent> {
        @Override
        public void renderComponent(PropertyButtonTabComponent component) {
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    (component.isHovered() ? RainbowUtil.INSTANCE.getColor().darker().getRGB() : Color.BLACK.getRGB()));
            componentFont.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (componentFont.getStringHeight(component.getName()) / 2f)) + 3f, Color.WHITE.getRGB());

            if (component.isState()) {
                RenderUtil.drawRect(component.getRenderPosition().getX() + component.getRenderPosition().getWidth() - 10,
                        component.getRenderPosition().getY(), component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                        component.getRenderPosition().getY() + component.getRenderPosition().getHeight(), RainbowUtil.INSTANCE.getColor().darker().darker().darker().getRGB());
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
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), component.isExtended() ? RainbowUtil.INSTANCE.getColor().darker().darker().getRGB() : 0xFF2B2C31);
            RenderUtil.drawRect(position.getX(), position.getY() + 3, position.getX() + position.getWidth(), position.getY() + frameHeight, 0xFF18191C);

            titleFont.drawStringWithShadow(component.getName(), position.getX() + (position.getWidth() / 2f) - (titleFont.getStringWidth(component.getName()) / 2f), position.getY() + 8, 0xFFFAFAFB);
        }
    }

    /**
     * The component renderer for plugin buttons.
     */
    private class PluginButtonComponentRenderer extends AbstractComponentRenderer<PluginButtonContainerComponent> {
        @Override
        public void renderComponent(PluginButtonContainerComponent component) {
            Rectangle position = component.getRenderPosition();

            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), component.getPlugin().isEnabled() ? RainbowUtil.INSTANCE.getColor().darker().darker().getRGB() : 0xFF2B2C31);
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

            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), component.getProperty().getValue() ? RainbowUtil.INSTANCE.getColor().darker().darker().getRGB() : 0xFF2B2C31);
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

            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), RainbowUtil.INSTANCE.getColor().darker().darker().getRGB());
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
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + component.getLength(), position.getY() + position.getHeight(), RainbowUtil.INSTANCE.getColor().darker().darker().getRGB());
            componentFont.drawStringWithShadow(component.getProperty().getLabel() + " " + component.getProperty().getValue().toString(), position.getX() + (position.getWidth() / 2f) - (componentFont.getStringWidth(component.getProperty().getLabel() + " " + component.getProperty().getValue().toString()) / 2), position.getY() + 5, 0xFFFAFAFB);
        }
    }
}
