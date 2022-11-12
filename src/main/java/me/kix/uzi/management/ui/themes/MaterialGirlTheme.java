package me.kix.uzi.management.ui.themes;

import me.kix.sodapop.components.frame.FrameContainerComponent;
import me.kix.sodapop.theme.AbstractTheme;
import me.kix.sodapop.theme.renderer.AbstractComponentRenderer;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.CoordinatesBlockComponent;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.ToggleablesBlockComponent;
import me.kix.uzi.management.plugin.internal.toggleable.render.ui.components.WatermarkComponent;
import me.kix.uzi.management.ui.click.component.buttons.PluginButtonContainerComponent;
import me.kix.uzi.management.ui.click.component.buttons.PropertyButtonComponent;
import me.kix.uzi.management.ui.click.component.console.ConsoleComponent;
import me.kix.uzi.management.ui.click.component.sliders.NumberPropertySliderComponent;
import me.kix.uzi.management.ui.click.component.spinners.EnumPropertySpinnerComponent;
import me.kix.uzi.management.ui.tab.item.impl.FolderTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.buttons.PropertyButtonTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.buttons.ToggleablePluginButtonTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.focus.SliderTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.focus.SpinnerTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.folders.ToggleablePluginFolderTabComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

/**
 * Material gworllllllll.
 *
 * @author Kix
 * @since 1/12/2022
 */
public class MaterialGirlTheme extends AbstractTheme {

    /**
     * The font for title panels.
     */
    private final NahrFont titleFont = new NahrFont("Verdana", 12);

    /**
     * The font renderer for the UI.
     */
    private final NahrFont font = new NahrFont("Consolas", 12);

    /**
     * The current accent color of the theme.
     */
    private final Color accent = new Color(0xFF37E4);

    public MaterialGirlTheme() {
        super("MaterialGworl", 200, 16, 12, 2, 2);
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
            font.drawStringWithShadow("material \247dgworl", component.getRenderPosition().getX(), component.getRenderPosition().getY() + 2, Color.WHITE.getRGB());
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
                font.drawStringWithShadow(coords, component.getRenderPosition().getX(), component.getRenderPosition().getY(), Color.PINK.darker().darker().getRGB());
                font.drawStringWithShadow(overworld, component.getRenderPosition().getX() + font.getStringWidth(coords) + 2, component.getRenderPosition().getY(), Color.PINK.getRGB());
            } else {
                font.drawStringWithShadow(coords, component.getRenderPosition().getX(), component.getRenderPosition().getY(), Color.PINK.getRGB());
            }
        }
    }

    /**
     * Tabgui folders.
     */
    private class FolderTabComponentRenderer extends AbstractComponentRenderer<FolderTabComponent> {
        @Override
        public void renderComponent(FolderTabComponent component) {

            Rectangle position = component.getRenderPosition();

            GL11.glPushMatrix();
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), 0xFF1D1D1D, 0xFF000000);
            RenderUtil.verticalGradientRectangle(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, 0xFF292929, 0xFF1D1D1D);
            GL11.glColor4f(1f, 1f, 1f, 1f);
            GL11.glPopMatrix();

            if(component.isHovered()) {
                GL11.glPushMatrix();
                RenderUtil.border(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, 2f, Color.BLACK.getRGB());
                RenderUtil.verticalGradientRectangle(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, accent.darker().darker().getRGB(), accent.darker().darker().darker().getRGB());
                GL11.glColor4f(1f, 1f, 1f, 1f);
                GL11.glPopMatrix();
            }
            font.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2.5f, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 4f, component.isHovered() ? Color.WHITE.getRGB() : Color.GRAY.getRGB());
            GL11.glEnable(GL11.GL_BLEND);
        }
    }

    private class ToggleablePluginFolderTabComponentRenderer extends AbstractComponentRenderer<ToggleablePluginFolderTabComponent> {
        @Override
        public void renderComponent(ToggleablePluginFolderTabComponent component) {
            Rectangle position = component.getRenderPosition();
            GL11.glPushMatrix();
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), 0xFF1D1D1D, 0xFF000000);
            RenderUtil.verticalGradientRectangle(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, 0xFF292929, component.getPlugin().isEnabled() ? accent.darker().darker().getRGB() : 0xFF1D1D1D);
            GL11.glColor4f(1f, 1f, 1f, 1f);
            GL11.glPopMatrix();

            if(component.isHovered()) {
                GL11.glPushMatrix();
                RenderUtil.border(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, 2f, Color.BLACK.getRGB());
                RenderUtil.verticalGradientRectangle(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, accent.darker().darker().getRGB(), accent.darker().darker().darker().getRGB());
                GL11.glColor4f(1f, 1f, 1f, 1f);
                GL11.glPopMatrix();
            }
            font.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, component.isHovered() ? Color.WHITE.getRGB() : Color.GRAY.getRGB());
            font.drawStringWithShadow("...", component.getRenderPosition().getX() +
                            component.getRenderPosition().getWidth() - font.getStringWidth("...") - 2f,
                    component.getRenderPosition().getY() + component.getRenderPosition().getHeight() - font.getStringHeight("...") + 3f, component.isHovered() ? Color.LIGHT_GRAY.getRGB() : Color.GRAY.getRGB());
        }
    }

    /**
     * Tabgui focus components.
     */
    private class SpinnerTabComponentRenderer extends AbstractComponentRenderer<SpinnerTabComponent> {
        @Override
        public void renderComponent(SpinnerTabComponent component) {
            Rectangle position = component.getRenderPosition();
            GL11.glPushMatrix();
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), 0xFF1D1D1D, 0xFF000000);
            RenderUtil.verticalGradientRectangle(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, 0xFF292929, 0xFF1D1D1D);
            GL11.glColor4f(1f, 1f, 1f, 1f);
            GL11.glPopMatrix();

            if(component.isHovered()) {
                GL11.glPushMatrix();
                RenderUtil.border(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, 2f, Color.BLACK.getRGB());
                RenderUtil.verticalGradientRectangle(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, accent.darker().darker().getRGB(), accent.darker().darker().darker().getRGB());
                GL11.glColor4f(1f, 1f, 1f, 1f);
                GL11.glPopMatrix();
            }
            font.drawStringWithShadow(component.getRaw(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, 0xFFFFFFFF);
        }
    }

    private class SliderTabComponentRenderer extends AbstractComponentRenderer<SliderTabComponent> {
        @Override
        public void renderComponent(SliderTabComponent component) {
            Rectangle position = component.getRenderPosition();
            GL11.glPushMatrix();
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), 0xFF1D1D1D, 0xFF000000);
            RenderUtil.verticalGradientRectangle(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, 0xFF292929, 0xFF1D1D1D);
            GL11.glColor4f(1f, 1f, 1f, 1f);
            GL11.glPopMatrix();

            if(component.isHovered()) {
                GL11.glPushMatrix();
                RenderUtil.border(position.getX() + 1, position.getY() + 1, position.getX() + component.getSliderLength() - 1, position.getY() + position.getHeight() - 1, 2f, Color.BLACK.getRGB());
                RenderUtil.verticalGradientRectangle(position.getX() + 1, position.getY() + 1, position.getX() + component.getSliderLength() - 1, position.getY() + position.getHeight() - 1, accent.darker().darker().getRGB(), accent.darker().darker().darker().getRGB());
                GL11.glColor4f(1f, 1f, 1f, 1f);
                GL11.glPopMatrix();
            }
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
            Rectangle position = component.getRenderPosition();
            GL11.glPushMatrix();
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), 0xFF1D1D1D, 0xFF000000);
            RenderUtil.verticalGradientRectangle(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, 0xFF292929, component.getPlugin().isEnabled() ? accent.darker().darker().getRGB() : 0xFF1D1D1D);
            GL11.glColor4f(1f, 1f, 1f, 1f);
            GL11.glPopMatrix();

            if(component.isHovered()) {
                GL11.glPushMatrix();
                RenderUtil.border(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, 2f, Color.BLACK.getRGB());
                RenderUtil.verticalGradientRectangle(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, accent.darker().darker().getRGB(), accent.darker().darker().darker().getRGB());
                GL11.glColor4f(1f, 1f, 1f, 1f);
                GL11.glPopMatrix();
            }
            font.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, component.isHovered() ? Color.WHITE.getRGB() : Color.GRAY.getRGB());
        }
    }

    private class PropertyButtonTabComponentRenderer extends AbstractComponentRenderer<PropertyButtonTabComponent> {
        @Override
        public void renderComponent(PropertyButtonTabComponent component) {
            Rectangle position = component.getRenderPosition();
            GL11.glPushMatrix();
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), 0xFF1D1D1D, 0xFF000000);
            RenderUtil.verticalGradientRectangle(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, 0xFF292929, component.isState() ? accent.darker().darker().getRGB() : 0xFF1D1D1D);
            GL11.glColor4f(1f, 1f, 1f, 1f);
            GL11.glPopMatrix();

            if(component.isHovered()) {
                GL11.glPushMatrix();
                RenderUtil.border(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, 2f, Color.BLACK.getRGB());
                RenderUtil.verticalGradientRectangle(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + position.getHeight() - 1, accent.darker().darker().getRGB(), accent.darker().darker().darker().getRGB());
                GL11.glColor4f(1f, 1f, 1f, 1f);
                GL11.glPopMatrix();
            }
            font.drawStringWithShadow(component.getName(), component.getRenderPosition().getX() + 2, (component.getRenderPosition().getY()
                    + (component.getRenderPosition().getHeight() / 2f) - (font.getStringHeight(component.getName()) / 2f)) + 3f, component.isHovered() ? Color.WHITE.getRGB() : Color.GRAY.getRGB());
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

            if (component.isExtended()) {
                GL11.glPushMatrix();
                RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + frameHeight, 0xFF1D1D1D, 0xFF000000);
                RenderUtil.verticalGradientRectangle(position.getX() + 1, position.getY() + 1, position.getX() + position.getWidth() - 1, position.getY() + frameHeight - 1, 0xFF292929, 0xFF1D1D1D);
                GL11.glColor4f(1f, 1f, 1f, 1f);
                GL11.glPopMatrix();
            }
            GL11.glPushMatrix();
            RenderUtil.border(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + frameHeight, 2f, Color.BLACK.getRGB());
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), accent.darker().darker().getRGB(), accent.darker().darker().darker().getRGB());
            GL11.glColor4f(1f, 1f, 1f, 1f);
            GL11.glPopMatrix();

            font.drawString(component.getName(), position.getX() + 2, position.getY() + (position.getHeight() / 2f), NahrFont.FontType.OUTLINE_THIN,
                    0xFFFFFFFF, 0xFF000000);


            font.drawString(component.isExtended() ? "-" : "+", position.getX() + position.getWidth() - 8f, position.getY() + (position.getHeight() / 2f), NahrFont.FontType.OUTLINE_THIN,
                    0xFFFFFFFF, 0xFF000000);
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
            GL11.glPushMatrix();
            RenderUtil.border(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), 2f, 0xFF000000);
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(),
                    component.getPlugin().isEnabled() ? accent.darker().getRGB() : 0xFF101010, component.getPlugin().isEnabled() ? accent.darker().darker().getRGB() : 0xFF191919);
            GL11.glColor4f(1f, 1f, 1f, 1f);
            GL11.glPopMatrix();
            font.drawString(component.getName(), position.getX() + 2, position.getY() + 6f, NahrFont.FontType.OUTLINE_THIN, 0xFFFFFFFF, 0xFF000000);

            if (!component.getComponents().isEmpty()) {
                font.drawString("...", position.getX() + position.getWidth() - font.getStringWidth("...") - 2, position.getY() + 7f, NahrFont.FontType.OUTLINE_THIN, component.isExtended() ? accent.getRGB() : 0xFFFFFFFF, 0xFF000000);
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
            GL11.glPushMatrix();
            RenderUtil.border(position.getX(), position.getY(), position.getX() + 12, position.getY() + position.getHeight(), 2f, 0xFF000000);
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + 12, position.getY() + position.getHeight(),
                    component.getProperty().getValue() ? accent.darker().getRGB() : 0xFF101010, component.getProperty().getValue() ? accent.darker().darker().getRGB() : 0xFF191919);
            GL11.glColor4f(1f, 1f, 1f, 1f);
            GL11.glPopMatrix();
            font.drawString(component.getName(), position.getX() + 14, position.getY() + 7f, NahrFont.FontType.OUTLINE_THIN, 0xFFFFFFFF, 0xFF000000);
        }
    }

    /**
     * The component renderer for property spinners.
     */
    private class EnumPropertySpinnerComponentRenderer extends AbstractComponentRenderer<EnumPropertySpinnerComponent> {
        @Override
        public void renderComponent(EnumPropertySpinnerComponent component) {
            Rectangle position = component.getRenderPosition();

            GL11.glPushMatrix();
            RenderUtil.border(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(), 2f, 0xFF000000);
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY(), position.getX() + position.getWidth(), position.getY() + position.getHeight(),
                    accent.darker().getRGB(), accent.darker().darker().getRGB());
            GL11.glColor4f(1f, 1f, 1f, 1f);
            GL11.glPopMatrix();
            font.drawString(component.getProperty().getLabel(), position.getX() + 2, position.getY() + 7f, NahrFont.FontType.OUTLINE_THIN, 0xFFFFFFFF, 0xFF000000);
            font.drawString(component.getProperty().getFixedValue(), position.getX() + position.getWidth() - font.getStringWidth(component.getProperty().getFixedValue()) - 2f,
                    position.getY() + 7f, NahrFont.FontType.OUTLINE_THIN, 0xFFFFFFFF, 0xFF000000);
        }
    }

    /**
     * The component renderer for property sliders.
     */
    private class NumberPropertySliderComponentRenderer extends AbstractComponentRenderer<NumberPropertySliderComponent> {
        @Override
        public void renderComponent(NumberPropertySliderComponent component) {
            Rectangle position = component.getRenderPosition();
            GL11.glPushMatrix();
            RenderUtil.border(position.getX(), position.getY() + position.getHeight() - 4, position.getX() + position.getWidth(), position.getY() + position.getHeight(), 2f, 0xFF000000);
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY() + position.getHeight() - 4, position.getX() + position.getWidth(), position.getY() + position.getHeight(), 0xFF101010, 0xFF191919);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            RenderUtil.border(position.getX(), position.getY() + position.getHeight() - 4, position.getX() + component.getLength(), position.getY() + position.getHeight(), 2f, 0xFF000000);
            RenderUtil.verticalGradientRectangle(position.getX(), position.getY() + position.getHeight() - 4, position.getX() + component.getLength(), position.getY() + position.getHeight(), accent.darker().getRGB(), accent.darker().darker().getRGB());
            GL11.glPopMatrix();
            font.drawString(component.getName(), position.getX() + 2, position.getY() + 3f, NahrFont.FontType.OUTLINE_THIN, 0xFFFFFFFF, 0xFF000000);
            font.drawString(component.getProperty().getValue().toString(),
                    position.getX() + position.getWidth() - titleFont.getStringWidth(component.getProperty().getValue().toString()) - 2,
                    position.getY() + 3f, NahrFont.FontType.OUTLINE_THIN, 0xFFFFFFFF, 0xFF000000);
        }
    }
}