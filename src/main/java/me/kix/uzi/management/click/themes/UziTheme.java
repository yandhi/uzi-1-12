package me.kix.uzi.management.click.themes;

import me.kix.sodapop.components.frame.FrameContainerComponent;
import me.kix.sodapop.theme.AbstractTheme;
import me.kix.sodapop.theme.renderer.AbstractComponentRenderer;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.Uzi;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.click.component.buttons.PluginButtonContainerComponent;
import me.kix.uzi.management.click.component.buttons.PropertyButtonComponent;
import me.kix.uzi.management.click.component.console.ConsoleComponent;
import me.kix.uzi.management.click.component.sliders.NumberPropertySliderComponent;
import me.kix.uzi.management.click.component.spinners.EnumPropertySpinnerComponent;
import me.kix.uzi.management.plugin.internal.toggleable.render.Overlay;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.CoordinatesBlockComponent;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.StringBlockComponent;
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
     * The font renderer for the UI.
     */
    private final NahrFont font = new NahrFont("Consolas", 16);

    /**
     * The current accent color of the theme.
     */
    private final Color uziColor = new Color(0x993940);

    public UziTheme() {
        super("Uzi", 115, 14, 15, 0, 2);
    }

    @Override
    public void initTheme() {
        getComponentRenderers().add(new FrameComponentRenderer());
        getComponentRenderers().add(new ConsoleComponentRenderer());
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
            font.drawStringWithShadow("Uzi", component.getRenderPosition().getX(), component.getRenderPosition().getY() + 2, Color.WHITE.getRGB());
        }
    }

    /**
     * Renders the toggleables.
     */
    private class ToggleablesBlockComponentRenderer extends AbstractComponentRenderer<ToggleablesBlockComponent> {

        @Override
        public void renderComponent(ToggleablesBlockComponent component) {
            List<ToggleablePlugin> plugins = component.getToggleables();
            plugins.sort((o1, o2) -> Float.compare(font.getStringWidth(o2.getDisplay()), font.getStringWidth(o1.getDisplay())));

            int y = component.getRenderPosition().getY() + 2;

            for (ToggleablePlugin plugin : plugins) {
                font.drawStringWithShadow(plugin.getDisplay(), component.getRenderPosition().getX() - font.getStringWidth(plugin.getDisplay()) - 2, y, plugin.getColor());
                y += font.getStringHeight(plugin.getDisplay()) - 1;
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
                font.drawStringWithShadow(coords, component.getRenderPosition().getX(), component.getRenderPosition().getY(), Color.RED.darker().darker().getRGB());
                font.drawStringWithShadow(overworld, component.getRenderPosition().getX() + font.getStringWidth(coords) + 2, component.getRenderPosition().getY(), Color.GRAY.getRGB());
            } else {
                font.drawStringWithShadow(coords, component.getRenderPosition().getX(), component.getRenderPosition().getY(), Color.GRAY.getRGB());
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
                    (component.isHovered() ? (component.isOpen() ? uziColor.darker().getRGB() : uziColor.getRGB()) : Color.BLACK.getRGB()));
            font.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2.5f, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, Color.WHITE.getRGB());
        }
    }

    private class ToggleablePluginFolderTabComponentRenderer extends AbstractComponentRenderer<ToggleablePluginFolderTabComponent> {
        @Override
        public void renderComponent(ToggleablePluginFolderTabComponent component) {
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    (component.isHovered() ? (component.isOpen() ? uziColor.darker().getRGB() : uziColor.getRGB()) : Color.BLACK.getRGB()));
            font.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, Color.WHITE.getRGB());
            font.drawStringWithShadow("...", component.getRenderPosition().getX() +
                            component.getRenderPosition().getWidth() - font.getStringWidth("...") - 2f,
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight() - font.getStringHeight("...") + 3f, Color.LIGHT_GRAY.getRGB());
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
                    (component.isHovered() ? uziColor.getRGB() : Color.BLACK.getRGB()));
            font.drawStringWithShadow(component.getRaw(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, 0xFFFFFFFF);
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
                    component.isHovered() ? uziColor.getRGB() : Color.BLACK.getRGB());
            font.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, 0xFFFFFFFF);
            font.drawStringWithShadow(component.getProperty().getValue().toString(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth() - font.getStringWidth(component.getProperty().getValue().toString()) - 2, (component.getRenderPosition().getY()
                            + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f,
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
                    (component.isHovered() ? uziColor.getRGB() : Color.BLACK.getRGB()));
            font.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, Color.WHITE.getRGB());

            if (component.isState()) {
                RenderUtil.drawRect(component.getRenderPosition().getX() + component.getRenderPosition().getWidth() - 10,
                        component.getRenderPosition().getY(), component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                        component.getRenderPosition().getY() + component.getRenderPosition().getHeight(), uziColor.darker().getRGB());
            }
        }
    }

    private class PropertyButtonTabComponentRenderer extends AbstractComponentRenderer<PropertyButtonTabComponent> {
        @Override
        public void renderComponent(PropertyButtonTabComponent component) {
            RenderUtil.drawRect(component.getRenderPosition().getX(), component.getRenderPosition().getY(),
                    component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight(),
                    (component.isHovered() ? uziColor.getRGB() : Color.BLACK.getRGB()));
            font.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, Color.WHITE.getRGB());

            if (component.isState()) {
                RenderUtil.drawRect(component.getRenderPosition().getX() + component.getRenderPosition().getWidth() - 10,
                        component.getRenderPosition().getY(), component.getRenderPosition().getX() + component.getRenderPosition().getWidth(),
                        component.getRenderPosition().getY() + component.getRenderPosition().getHeight(), uziColor.darker().getRGB());
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
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + frameHeight, 0xFF101010);
            RenderUtil.drawRect(position.getX() + position.getWidth() - 14, position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), component.isExtended() ? uziColor.getRGB() : 0xFF1F1F1F);
            titleFont.drawString(component.getName(), position.getX() + 2, position.getY() + 6f, NahrFont.FontType.NORMAL, 0xFF565656, -1);
        }
    }

    /**
     * The component renderer for the console.
     */
    private class ConsoleComponentRenderer extends AbstractComponentRenderer<ConsoleComponent> {
        @Override
        public void renderComponent(ConsoleComponent component) {
            Rectangle position = component.getRenderPosition();

            // Input box first ofc :)
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + 12, 0xFF000000);
            String username = Minecraft.getMinecraft().getSession().getUsername();
            titleFont.drawStringWithShadow("\2477[\2476" + username + "@\247cuzi\2477]\2478:\247f" + component.getText(), position.getX() + 2, position.getY(), 0xFFFFFFFF);

            // History box
            RenderUtil.drawRect(position.getX(), position.getY() + 12, position.getX() + position.getWidth(), position.getY() + getHeight(), 0xFF000000);
            int y = position.getY() + 12;

            for (String line : component.getPreviousInput()) {
                titleFont.drawStringWithShadow(line, position.getX() + 2, y, 0xFFFFFFFF);
                y += 12;
            }
        }
    }

    /**
     * The component renderer for plugin buttons.
     */
    private class PluginButtonComponentRenderer extends AbstractComponentRenderer<PluginButtonContainerComponent> {
        @Override
        public void renderComponent(PluginButtonContainerComponent component) {
            Rectangle position = component.getRenderPosition();
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), component.getPlugin().isEnabled() ? uziColor.getRGB() : 0xFF1F1F1F);
            titleFont.drawString(component.getName(), position.getX() + 2, position.getY() + 7f, NahrFont.FontType.SHADOW_THIN, 0xFF838383, 0xFF121212);

            if (!component.getComponents().isEmpty()) {
                titleFont.drawString("...", position.getX() + position.getWidth() - titleFont.getStringWidth("...") - 2, position.getY() + 7f, NahrFont.FontType.SHADOW_THICK, component.isExtended() ? uziColor.getRGB() : 0xFF838383, 0xFF121212);
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
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), component.getProperty().getValue() ? uziColor.getRGB() : 0xFF1F1F1F);
            RenderUtil.drawRect(position.getX() + 14, position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight() - 1, 0xFF1F1F1F);
            titleFont.drawString(component.getName(), position.getX() + 16, position.getY() + 7f, NahrFont.FontType.SHADOW_THIN, 0xFF838383, 0xFF121212);
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
            titleFont.drawString(component.getName(), position.getX() + 2, position.getY() + 7f, NahrFont.FontType.SHADOW_THIN, 0xFF565656, 0xFF131313);
            titleFont.drawString(component.getProperty().getFixedValue(),
                    position.getX() + position.getWidth() - titleFont.getStringWidth(component.getProperty().getFixedValue()) - 2,
                    position.getY() + 7f, NahrFont.FontType.SHADOW_THIN, 0xFF565656, 0xFF131313);
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
            RenderUtil.drawRect(position.getX(), position.getY(), position.getX() + component.getLength(), position.getY() + position.getHeight(), uziColor.getRGB());
            RenderUtil.drawRect(position.getX() + component.getLength() - 2, position.getY(), position.getX() + component.getLength(), position.getY() + position.getHeight(), uziColor.darker().getRGB());
            titleFont.drawString(component.getName(), position.getX() + 2, position.getY() + 7f, NahrFont.FontType.SHADOW_THIN, 0xFF838383, 0xFF121212);
            titleFont.drawString(component.getProperty().getValue().toString(),
                    position.getX() + position.getWidth() - titleFont.getStringWidth(component.getProperty().getValue().toString()) - 2,
                    position.getY() + 7f, NahrFont.FontType.SHADOW_THIN, 0xFF838383, 0xFF121212);
        }
    }
}